package healthApp_backend.entity;

import healthApp_backend.dto.user.SocialUserRequest;
import healthApp_backend.dto.user.UserRequest;
import healthApp_backend.dto.user.UserResponse;
import healthApp_backend.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Routine>  routines = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutRecord>  workoutRecords = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DietRecord>  dietRecords = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StretchingRoutine>  stretchingRoutines = new ArrayList<>();

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

    // 나중에 추가할 것들 → 지금 추가!
    private Integer targetCalories;   // 목표 칼로리
    private Integer targetProtein;    // 목표 단백질

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

    public void updateTarget(Integer targetCalories, Integer targetProtein) {
        this.targetCalories = targetCalories;
        this.targetProtein = targetProtein;
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
