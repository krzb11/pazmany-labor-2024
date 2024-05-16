package hu.pazmany.repository;

import hu.pazmany.model.Session;
import hu.pazmany.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findBySessionKeyAndUser(String sessionKey, User user);

    Optional<Session> findBySessionKey(String sessionKey);
}
