package investlog_backend.dto.user;

import investlog_backend.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserRequest {

    @NotBlank(groups = ValidationGroups.NotBlankGroups.class, message = "아이디 입력은 필수입니다.")
    private String username;

    @NotBlank(groups = ValidationGroups.NotBlankGroups.class, message = "비밀번호 입력은 필수입니다.")
    private String password;
}
