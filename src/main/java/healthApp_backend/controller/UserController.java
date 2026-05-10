package healthApp_backend.controller;

import healthApp_backend.dto.user.*;
import healthApp_backend.entity.User;
import healthApp_backend.enums.Role;
import healthApp_backend.security.auth.CustomUserDetails;
import healthApp_backend.security.auth.CustomUserDetailsService;
import healthApp_backend.service.TokenBlacklistService;
import healthApp_backend.service.UserService;
import healthApp_backend.util.JwtUtil;
import healthApp_backend.validation.CustomValidators;
import healthApp_backend.validation.ValidationSequence;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenBlacklistService tokenBlacklistService;
    private final CustomValidators validators;
    private final JwtUtil jwtUtil;

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring(7);
            long expirationTime = jwtUtil.extractAllClaims(accessToken).getExpiration().getTime() - System.currentTimeMillis();

            // 토큰을 블랙리스트에 추가
            tokenBlacklistService.addToBlacklist(accessToken, expirationTime);
        }

        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body("로그아웃 되었습니다.");
    }

    /**
     * 회원가입
     */
    @PostMapping
    public ResponseEntity<?> registerUser(@Validated(ValidationSequence.class) @RequestBody UserRequest dto,
                                          BindingResult bindingResult) {
        validators.joinValidateAll(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return buildValidationErrorResponse(bindingResult);
        }
        UserResponse userResponse = userService.createUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    /**
     * 마이페이지
     */
    @GetMapping
    public ResponseEntity<?> getUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponse userResponse = userService.getUser(userDetails.getId());

        return ResponseEntity.ok(userResponse);
    }

    /**
     * 회원 수정
     */
    @PatchMapping
    public ResponseEntity<?> updateUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @Validated(ValidationSequence.class) @RequestBody UserSimpleRequest dto,
                                               BindingResult bindingResult) {
//        validators.modifyValidateAll(userRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            return buildValidationErrorResponse(bindingResult);
        }
        //회원 수정
        userService.updateUser(userDetails.getId(), dto);
        UserResponse updatedUser = userService.getUser(userDetails.getId());

        return ResponseEntity.ok(updatedUser);

    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteUser(userDetails.getId());

        return ResponseEntity.ok("User Deleted successfully");
    }

    /**
     * 비밀번호 찾기
     */
    @PostMapping("/find-password")
    public ResponseEntity<Object> resetPassword(@Validated(ValidationSequence.class) @RequestBody FindPasswordRequest dto) {
        UserResponse userResponse = userService.findUserForPasswordReset(dto.getUsername(), dto.getEmail());
        String temporaryPassword = PasswordGenerator.generatePassword(10);
        userService.updatePassword(userResponse.getUserId(), temporaryPassword);
        FindPasswordResponse response = new FindPasswordResponse("임시 비밀번호는 " + temporaryPassword + "입니다.");

        return ResponseEntity.ok(response);
    }

    /**
     * 구글 소셜 로그인
     */
    @PostMapping("/google-login")
    public ResponseEntity<?> loginWithGoogle(@RequestBody UserInfo userInfo) {
        return handleSocialLogin(userInfo);
    }

    /**
     * 카카오 소셜 로그인
     */
    @PostMapping("/kakao-login")
    public ResponseEntity<?> loginWithKakao(@RequestBody UserInfo userInfo) {
        return handleSocialLogin(userInfo);
    }

    /**
     * refreshToken 갱신
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No refresh token provided");
        }

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refresh_token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (refreshToken == null || jwtUtil.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }

        // 블랙리스트에 있는지 확인
        if (tokenBlacklistService.isBlacklisted(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is blacklisted.");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        String newAccessToken = jwtUtil.generateToken(username);

        return ResponseEntity.ok("{\"accessToken\": \"" + newAccessToken + "\"}");
    }

    private ResponseEntity<?> buildValidationErrorResponse(BindingResult bindingResult) {
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errorMessages);
    }

    private ResponseEntity<?> handleSocialLogin(UserInfo userInfo) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(userInfo.getEmail());
            User user = userDetails.getUser();

            if (user.getRole() == Role.SOCIAL) {
                return generateJwtResponse(user.getUsername());
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일입니다. 일반 로그인을 사용하세요.");
            }
        } catch (UsernameNotFoundException e) {
            UserResponse newUserResponse = userService.createUser(UserRequest.from(userInfo));
            return generateJwtResponse(newUserResponse.getUsername());
        }
    }

    private ResponseEntity<JwtResponse> generateJwtResponse(String username) {
        String accessToken = jwtUtil.generateToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        JwtResponse jwtResponse = JwtResponse.from(accessToken, refreshToken, username, jwtUtil.getExpiration(), jwtUtil.getRefreshExpiration());
        return ResponseEntity.ok(jwtResponse);
    }
}