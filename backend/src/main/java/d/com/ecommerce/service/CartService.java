package d.com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import d.com.ecommerce.entity.CartItem;
import d.com.ecommerce.entity.Product;
import d.com.ecommerce.entity.User;
import d.com.ecommerce.repository.CartItemRepository;
import d.com.ecommerce.repository.ProductRepository;

/**
 * カート内の商品の取得、登録、更新、削除機能を提供する
 */
@Service
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * ユーザーに紐づくカート内の商品を取得する
     * @param user ユーザー情報
     * @return ユーザーに紐づくカート内の商品一覧
     */
    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    /**
     * カート内に商品を追加する
     * @param user ユーザー情報
     * @param productId 商品Id
     * @param quantity 商品数
     */
    public void addItemToCart(User user, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
                .orElse(new CartItem());

        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemRepository.save(cartItem);
    }

    /**
     * カート内の商品を削除する
     * @param user ユーザー情報
     * @param productId 削除する商品Id
     */
    public void removeItemFromCart(User user, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        cartItemRepository.delete(cartItem);
    }

    /**
     * カート内の商品を更新する
     * @param user ユーザー情報
     * @param productId 更新する商品Id
     * @param quantity 商品数
     */
    public void updateItemQuantity(User user, Long productId, int quantity) {
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
