package d.com.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import d.com.ecommerce.entity.User;
import d.com.ecommerce.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User registerUser(String username, String password) {
		if (userRepository.findByUsername(username).isPresent()) {
			throw new RuntimeException("Username already taken");
		}
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole("USER");
		
		return userRepository.save(user);
	}
	
	public Optional<User> getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public Optional<User> getUserById(Long userId) {
		return userRepository.findById(userId);
	}
}
