package d.com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import d.com.ecommerce.entity.CartItem;
import d.com.ecommerce.entity.User;
import d.com.ecommerce.service.CartService;
import d.com.ecommerce.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<CartItem> getCartItems(@RequestParam Long userId) {
        User user = userService.getUserByUserId(userId);
        return cartService.getCartItems(user);
    }

    @PostMapping("/add")
    public void addItemToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        User user = userService.getUserByUserId(userId);
        cartService.addItemToCart(user, productId, quantity);
    }

    @DeleteMapping("/remove")
    public void removeItemFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        User user = userService.getUserByUserId(userId);
        cartService.removeItemFromCart(user, productId);
    }

    @PutMapping("/update")
    public void updateItemQuantity(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        User user = userService.getUserByUserId(userId);
        cartService.updateItemQuantity(user, productId, quantity);
    }
}
