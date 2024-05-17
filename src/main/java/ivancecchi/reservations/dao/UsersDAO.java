package ivancecchi.reservations.dao;

import ivancecchi.reservations.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersDAO extends JpaRepository<User, UUID> {
    public Optional<User> findFirstByEmail(String email);

    public Optional<User> findFirstByUsername(String username);


    public Optional<User> findFirstByUsernameOrEmailIgnoreCase(String username, String email);
}
