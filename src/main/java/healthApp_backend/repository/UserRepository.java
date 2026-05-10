package healthApp_backend.repository;

import healthApp_backend.entity.User;
import healthApp_backend.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);

    Optional<User> findByUsernameAndEmail(String username, String email);

    Optional<User> findByUsernameAndPhoneNumber(String username, String phoneNumber);
}
