package d.com.ecommerce.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import d.com.ecommerce.entity.User;
import d.com.ecommerce.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password) {
		try {
			User user = userService.registerUser(username, password);
			logger.info("User registered: {}", user.getUsername());
			return ResponseEntity.ok("User registered successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/login")
	public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
		Optional<User> user = userService.getUserByUsername(username);
		if (user.isPresent() && password.equals(user.get().getPassword())) {
			return ResponseEntity.ok("Login successful");
		} else {
			return ResponseEntity.badRequest().body("Invalid username or password");
		}
	}
}
