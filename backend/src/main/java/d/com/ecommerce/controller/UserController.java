package d.com.ecommerce.controller;

import java.util.HashMap;
import java.util.Map;

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

/**
 * ユーザー情報を管理するエンドポイントを提供する
 * ユーザーに関連するCRUD操作（作成、取得、更新、削除）を処理する
 */
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
	
	/**
	 * ユーザーを新規登録する
	 * @param userMap ユーザー名とPW
	 * @return 登録成功時はレスポンス200を返す
	 */
	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> userMap) {
		Map<String, Object> response = new HashMap<>();
		try {
			String username = userMap.get("username");
			String password = userMap.get("password");

			if (username == null || password == null) {
				response.put("message", "Username and password must be provided");
				return ResponseEntity.badRequest().body(response);
			}

			User user = userService.registerUser(username, password);
			logger.info("User registered: {}", user.getUsername());
			
			// トークンを生成
			String token = jwtUtils.generateJwtToken(user);
			Long userId = user.getId();

			// レスポンスにトークンとユーザーIDを追加
			response.put("message", "User registered successfully");
			response.put("token", token);
			response.put("userId", userId);
			
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			response.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	/**
	 * 登録済みのユーザー情報を確認しログインを制御する
	 * @param userMap ユーザー名とPW
	 * @return ログイン成功時はレスポンス200とToken、UserIdを返却
	 */
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> userMap) {

		String username = userMap.get("username");
		String password = userMap.get("password");
		Map<String, Object> response = new HashMap<>();

		if (username == null || password == null) {
			response.put("message", "Username and password must be provided");
			return ResponseEntity.badRequest().body(response);
		}

		User user = userService.getUserByUsername(username);
		if (passwordEncoder.matches(password, user.getPassword())) {
			String token = jwtUtils.generateJwtToken(user);
			Long userId = user.getId();
			
			response.put("message", "Login successful");
			response.put("token", token);
			response.put("userId", userId);
			
			return ResponseEntity.ok(response);
		} else {
			response.put("message", "Invalid username or password");
			return ResponseEntity.badRequest().body(response);
		}
	}
}
