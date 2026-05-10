package healthApp_backend.repository.custom;

import healthApp_backend.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<User> findByUsername(String username);

    Optional<User> findByNameAndUsernameAndEmail(String name, String username, String email);

}
