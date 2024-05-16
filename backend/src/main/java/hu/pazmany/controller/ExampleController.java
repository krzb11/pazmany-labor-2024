package hu.pazmany.controller;

import hu.pazmany.model.Session;
import hu.pazmany.model.User;
import hu.pazmany.model.UserScore;
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
import java.util.ArrayList;
import java.util.List;
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
		// Assuming registration creates a new user and session key
		User newUser = userService.registerNewUser(user); // Method to be implemented
		String sessionKey = userService.generateSessionKey(newUser);
		return ResponseEntity.ok(Map.of("username", newUser.getUsername(), "sessionkey", sessionKey, "expire", "24 hours from now"));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		Optional<User> existingUser = userService.validateUserCredentials(user.getUsername(), user.getPassword());
		if (existingUser.isEmpty()) {
			if (!userService.isUserExists(user.getUsername())) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(Map.of("username", user.getUsername(), "error", "username is unknown"));
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("error", "password is wrong"));
		}
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
		if (validSession.isEmpty() || validSession.get().getExpiration().isBefore(Instant.now())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Session is expired"));
		}
		List<UserScore> leaderboard = userService.getTopScores();
		Optional<UserScore> requestingUserScore = userService.getUserScore(request.get("username"));
		List<UserScore> result = new ArrayList<>(leaderboard);
		requestingUserScore.ifPresent(score -> result.add(score)); // Append if not in top 10
		return ResponseEntity.ok(result);
	}

	// Additional endpoints for logout, leaderboard, etc.
}
