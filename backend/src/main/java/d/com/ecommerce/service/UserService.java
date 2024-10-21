package d.com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import d.com.ecommerce.entity.User;
import d.com.ecommerce.exception.UserNotFoundException;
import d.com.ecommerce.repository.UserRepository;

/**
 * ユーザーの登録、検索、取得機能を提供する
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 新規ユーザーを登録し、ユーザー名が既に存在する場合は例外をスローする
     *
     * @param username 登録するユーザーの名前
     * @param password 登録するユーザーのパスワード
     * @return 保存されたユーザーエンティティ
     * @throws UserNotFoundException ユーザー名が既に存在する場合
     */
    public User registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserNotFoundException("Username already taken");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");

        return userRepository.save(user);
    }

    /**
     * ユーザー名を基にユーザーを取得し、ユーザーが存在しない場合は例外をスローする
     *
     * @param username 検索するユーザーの名前
     * @return 該当するユーザーエンティティ
     * @throws UserNotFoundException ユーザーが存在しない場合
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    /**
     * ユーザーIDを基にユーザーを取得し、ユーザーが存在しない場合は例外をスローする
     *
     * @param userId 検索するユーザーのID
     * @return 該当するユーザーエンティティ
     * @throws UserNotFoundException ユーザーが存在しない場合
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
