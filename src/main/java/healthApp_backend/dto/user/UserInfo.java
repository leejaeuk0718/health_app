package healthApp_backend.dto.user;


import healthApp_backend.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {


    private Long userId;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String name;
    private String phoneNumber;
    private Role role;
}
