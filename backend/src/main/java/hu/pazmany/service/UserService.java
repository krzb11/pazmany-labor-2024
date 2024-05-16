package hu.pazmany.service;

import hu.pazmany.model.Session;
import hu.pazmany.model.User;
import hu.pazmany.repository.SessionRepository;
import hu.pazmany.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        // Logic to generate and save session key
    }

    public boolean invalidateSessionKey(String sessionKey) {
        // Logic to invalidate session key
    }

    public List<UserScore> getTopScores() {
        // Logic to fetch top scores
    }

    public Optional<Session> getSessionByKey(String sessionKey) {
        return sessionRepository.findBySessionKey(sessionKey);
    }
}

