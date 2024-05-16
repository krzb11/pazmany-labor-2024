package hu.pazmany.service;

import hu.pazmany.model.Session;
import hu.pazmany.model.User;
import hu.pazmany.model.UserScore;
import hu.pazmany.repository.SessionRepository;
import hu.pazmany.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;

    public Optional<User> validateUserCredentials(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }

    public String generateSessionKey(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setSessionKey(UUID.randomUUID().toString());
        session.setExpiration(Instant.now().plusSeconds(86400)); // 24 hours validity
        sessionRepository.save(session);
        return session.getSessionKey();
    }

    public boolean invalidateSessionKey(String sessionKey) {
        Optional<Session> sessionOpt = sessionRepository.findBySessionKey(sessionKey);
        if (sessionOpt.isPresent()) {
            sessionRepository.delete(sessionOpt.get());
            return true;
        }
        return false;
    }

    public List<UserScore> getTopScores() {
        // Assuming you have a method in UserRepository that can fetch users with their scores
        // This is a placeholder for whatever your actual data retrieval logic would be
        return userRepository.findAll().stream()
                .map(user -> new UserScore(user.getUsername(), user.getScore())) // Assuming User has a getScore method
                .sorted((u1, u2) -> Integer.compare(u2.getScore(), u1.getScore()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public Optional<Session> getSessionByKey(String sessionKey) {
        return sessionRepository.findBySessionKey(sessionKey);
    }

    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    public boolean isEmailAvailable(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    public User registerNewUser(User user) {
        if (isUsernameAvailable(user.getUsername()) && isEmailAvailable(user.getEmail())) {
            // Assuming User class has been properly set up with @Entity annotation and all required fields
            return userRepository.save(user); // Save the new user in the database
        } else {
            throw new IllegalStateException("Username or email already in use.");
        }
    }

    public boolean isUserExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public Optional<UserScore> getUserScore(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new UserScore(user.getUsername(), user.getScore())); // Assuming User has a getScore method
    }
}

