package d.com.ecommerce.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import d.com.ecommerce.config.JwtUtils;
import d.com.ecommerce.entity.User;
import d.com.ecommerce.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Map<String, String> userMap) {
		try {
			String username = userMap.get("username");
			String password = userMap.get("password");

			if (username == null || password == null) {
				return ResponseEntity.badRequest().body("Username and password must be provided");
			}

			User user = userService.registerUser(username, password);
			logger.info("User registered: {}", user.getUsername());
			return ResponseEntity.ok("User registered successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> userMap) {

		String username = userMap.get("username");
		String password = userMap.get("password");
		Map<String, Object> response = new HashMap<>();

		if (username == null || password == null) {
			response.put("message", "Username and password must be provided");
			return ResponseEntity.badRequest().body(response);
		}

		Optional<User> user = userService.getUserByUsername(username);
		if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
			String token = jwtUtils.generateJwtToken(user.get());
			
			response.put("message", "Login successful");
			response.put("token", token);
			logger.info("token:", token);
			
			return ResponseEntity.ok(response);
		} else {
			response.put("message", "Invalid username or password");
			return ResponseEntity.badRequest().body(response);
		}
	}
}
