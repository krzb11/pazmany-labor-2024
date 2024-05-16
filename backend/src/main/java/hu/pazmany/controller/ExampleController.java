package hu.pazmany.controller;

import hu.pazmany.model.Session;
import hu.pazmany.model.User;
import hu.pazmany.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ExampleController {
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {
		if (!userService.isUsernameAvailable(user.getUsername())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("username", user.getUsername(), "error", "username already taken"));
		}
		if (!userService.isEmailAvailable(user.getEmail())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("email", user.getEmail(), "error", "email already taken"));
		}
		// Register user logic here
		return ResponseEntity.ok(Map.of("username", user.getUsername(), "sessionkey", "generatedSessionKey", "expire", "timestamp"));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		Optional<User> existingUser = userService.validateUserCredentials(user.getUsername(), user.getPassword());
		if (existingUser.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("error", "Invalid username or password"));
		}
		// Assume generateSessionKey creates a new session key
		String sessionKey = userService.generateSessionKey(existingUser.get());
		return ResponseEntity.ok(Map.of("username", user.getUsername(), "sessionkey", sessionKey, "expire", "24 hours from now"));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody Map<String, String> request) {
		boolean result = userService.invalidateSessionKey(request.get("sessionkey"));
		if (!result) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid session or already logged out"));
		}
		return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
	}

	@PostMapping("/leaderboard")
	public ResponseEntity<?> getLeaderboard(@RequestBody Map<String, String> request) {
		Optional<Session> validSession = userService.getSessionByKey(request.get("sessionkey"));
		if (validSession.isEmpty() || validSession.get().getExpiration().isBefore(Instant.from(LocalDateTime.now()))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Session is expired or invalid"));
		}

		List<UserScore> leaderboard = userService.getTopScores();
		return ResponseEntity.ok(leaderboard);
	}

	// Additional endpoints for logout, leaderboard, etc.
}
