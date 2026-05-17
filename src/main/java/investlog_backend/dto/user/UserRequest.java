package investlog_backend.dto.user;

import investlog_backend.enums.Role;
import investlog_backend.validation.ValidationGroups;
import investlog_backend.validation.ValidationGroups.NotBlankGroups;
import investlog_backend.validation.ValidationGroups.PatternGroups;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {


    @NotBlank(message = "이름은 필수 입력 값입니다", groups = ValidationGroups.NotBlankGroups.class)
    private String name;

    @NotBlank(message = "아이디는 필수 입력 값입니다.", groups = ValidationGroups.NotBlankGroups.class)
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "아이디는 특수문자를 제외한 4~20자 사이로 입력해주세요.", groups = PatternGroups.class)
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.", groups = NotBlankGroups.class)
//    @Range(min = 8, max = 16, message = "비밀번호는 8~16자 사이로 입력해주세요.", groups = RangeGroups.class)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,16}$", message = "비밀번호는 영문, 숫자, 특수문자 모두 포함해주세요.", groups = PatternGroups.class)
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.", groups = NotBlankGroups.class)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "닉네임은 2~10자의 한글, 영문, 숫자만 입력해주세요.", groups = PatternGroups.class)
    private String nickname;

    @NotBlank(message = "이메일은 필수 입력 값입니다.", groups = NotBlankGroups.class)
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?", message = "이메일 형식이 올바르지 않습니다.", groups = PatternGroups.class)
    private String email;

    @NotBlank(message = "휴대폰 번호는 필수 입력 값입니다.", groups = NotBlankGroups.class)
    @Pattern(regexp = "^01(?:0|1|[6-9])[0-9]{7,8}$", message = "올바른 휴대폰 번호 형식이 아닙니다.", groups = PatternGroups.class)
    private String phoneNumber;

    private Role role;

    public static UserRequest from(UserInfo userInfo){
        return UserRequest.builder()
                .name(userInfo.getName())
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .nickname(userInfo.getNickname())
                .email(userInfo.getEmail())
                .phoneNumber(userInfo.getPhoneNumber())
                .role(userInfo.getRole())
                .build();
    }}
