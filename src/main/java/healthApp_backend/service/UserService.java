package healthApp_backend.service;

import healthApp_backend.dto.user.UserRequest;
import healthApp_backend.dto.user.UserResponse;
import healthApp_backend.dto.user.UserSimpleRequest;
import healthApp_backend.entity.User;
import healthApp_backend.exception.UserNotFoundException;
import healthApp_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    /**
     * 회원가입
     */
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        //password 인코딩
        userRequest.setPassword(encoder.encode(userRequest.getPassword()));
        User user = User.from(userRequest);
        User savedUser = userRepository.save(user);

        return UserResponse.from(savedUser);
    }

    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return UserResponse.from(user);
    }

    public String getUsername(String username, String phoneNumber) {
        User user = userRepository.findByUsernameAndPhoneNumber(username, phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        return user.getUsername();
    }

    /**
     * 비밀번호 찾기
     */
    public UserResponse findUserForPasswordReset(String username, String email) {
        User user = userRepository.findByUsernameAndEmail(username, email)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        return UserResponse.from(user);

    }

    @Transactional
    public void updateUser(Long userId, UserSimpleRequest userSimpleRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        //user 정보 수정
        user.update(userSimpleRequest.getPassword(), userSimpleRequest.getNickname(),userSimpleRequest.getUsername());
    }

    @Transactional
    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.updatePassword(encoder.encode(newPassword));
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        //user 삭제
        userRepository.delete(user);
    }

}
