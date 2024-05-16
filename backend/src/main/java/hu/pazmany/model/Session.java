package hu.pazmany.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.Instant;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sessionKey;
    private Instant expiration;
    @ManyToOne
    private User user;

    // Getters
    public Long getId() {
        return id;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
