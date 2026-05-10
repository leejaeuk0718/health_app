package healthApp_backend.dto.user;

import healthApp_backend.entity.User;
import healthApp_backend.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserResponse {

    private Long userId;
    private String username;
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;
    private Role role;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }
}
