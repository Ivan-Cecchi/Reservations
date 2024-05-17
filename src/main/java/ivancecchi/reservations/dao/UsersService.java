package ivancecchi.reservations.dao;

import lombok.extern.slf4j.Slf4j;
import ivancecchi.reservations.entities.User;
import ivancecchi.reservations.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsersService {
    @Autowired
    private UsersDAO us;

    public void save(User user) {
        try {
            us.save(user);
            log.info("User saved: {}", user);
        } catch (DataIntegrityViolationException e) {
            log.warn("User already exists: {}", user);
        }
    }


    public User getByUsername(String username) {
        return us.findFirstByUsername(username).orElseThrow(() -> new RecordNotFoundException("User not found"));
    }

    public User getByUsernameOrEmail(String usernameOrEmail) {
        return us.findFirstByUsernameOrEmailIgnoreCase(usernameOrEmail, usernameOrEmail).orElseThrow(() -> new RecordNotFoundException("User not found"));
    }
}
