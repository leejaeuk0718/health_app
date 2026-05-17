package investlog_backend.entity;

import investlog_backend.dto.user.SocialUserRequest;
import investlog_backend.dto.user.UserRequest;
import investlog_backend.dto.user.UserResponse;
import investlog_backend.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nick_name", nullable = false, unique = true)
    private String nickname;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;


    @Enumerated(EnumType.STRING)
    private Role role;


    //비지니스 로직
    public void update(String username,String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
        this.username = username;
    }

    //비지니스 로직
    public void updatePassword(String password) {
        this.password = password;
    }



    public static User from(SocialUserRequest userRequest) {
        return User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .nickname(userRequest.getNickname())
                .email(userRequest.getEmail())
                .role(userRequest.getRole())
                .build();
    }

    public static User from(UserResponse response) {
        return User.builder()
                .id(response.getUserId())
                .username(response.getUsername())
                .password(response.getPassword())
                .email(response.getEmail())
                .nickname(response.getNickname())
                .phoneNumber(response.getPhoneNumber())
                .role(response.getRole())
                .build();
    }

    public static User from(UserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .build();
    }

}
