package d.com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import d.com.ecommerce.entity.CartItem;
import d.com.ecommerce.entity.Product;
import d.com.ecommerce.entity.User;
import d.com.ecommerce.repository.CartItemRepository;
import d.com.ecommerce.repository.ProductRepository;

@Service
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    public void addItemToCart(Optional<User> userOpt, Long productId, int quantity) {
        User user = userOpt.orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
                .orElse(new CartItem());

        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemRepository.save(cartItem);
    }

    public void removeItemFromCart(Optional<User> userOpt, Long productId) {
        User user = userOpt.orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        cartItemRepository.delete(cartItem);
    }

    public void updateItemQuantity(Optional<User> userOpt, Long productId, int quantity) {
        User user = userOpt.orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }
}
