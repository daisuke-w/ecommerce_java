package d.com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import d.com.ecommerce.entity.CartItem;
import d.com.ecommerce.entity.Product;
import d.com.ecommerce.entity.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	List<CartItem> findByUser(User user);
	Optional<CartItem> findByUserAndProduct(User user, Product product);
}
