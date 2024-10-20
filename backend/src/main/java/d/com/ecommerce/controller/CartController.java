package d.com.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import d.com.ecommerce.dto.CartRequest;
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
    public List<CartItem> getCartItems(Principal principal) {
    	String username = principal.getName();
    	User user = userService.getUserByUsername(username);
        return cartService.getCartItems(user);
    }

    @PostMapping("/add")
    public void addItemToCart(@RequestBody CartRequest cartRequest, Principal principal) {
    	String username = principal.getName();
    	User user = userService.getUserByUsername(username);
    	
    	Long productId = cartRequest.getProductId();
    	int quantity = cartRequest.getQuantity();
        cartService.addItemToCart(user, productId, quantity);
    }

    @DeleteMapping("/remove")
    public void removeItemFromCart(@RequestParam Long productId, Principal principal) {
    	String username = principal.getName();
    	User user = userService.getUserByUsername(username);
        cartService.removeItemFromCart(user, productId);
    }

    @PutMapping("/update")
    public void updateItemQuantity(@RequestParam Long productId, @RequestParam int quantity, Principal principal) {
    	String username = principal.getName();
    	User user = userService.getUserByUsername(username);
        cartService.updateItemQuantity(user, productId, quantity);
    }
}
