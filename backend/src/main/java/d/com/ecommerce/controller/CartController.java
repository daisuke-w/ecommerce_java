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

/**
 * カート情報を管理するエンドポイントを提供する
 * カートに関連するCRUD操作（作成、取得、更新、削除）を処理する
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    /**
     * ユーザーに紐づくカート情報を取得する
     * @param principal ユーザー情報
     * @return ユーザーに紐づくカート情報
     */
    @GetMapping
    public List<CartItem> getCartItems(Principal principal) {
    	String username = principal.getName();
    	User user = userService.getUserByUsername(username);
        return cartService.getCartItems(user);
    }

    /**
     * カートに商品を追加する
     * @param cartRequest 追加する商品情報
     * @param principal ユーザー情報
     */
    @PostMapping("/add")
    public void addItemToCart(@RequestBody CartRequest cartRequest, Principal principal) {
    	String username = principal.getName();
    	User user = userService.getUserByUsername(username);
    	
    	Long productId = cartRequest.getProductId();
    	int quantity = cartRequest.getQuantity();
        cartService.addItemToCart(user, productId, quantity);
    }

    /**
     * カートから商品を削除する
     * @param productId 削除する商品Id
     * @param principal ユーザー情報
     */
    @DeleteMapping("/remove")
    public void removeItemFromCart(@RequestParam Long productId, Principal principal) {
    	String username = principal.getName();
    	User user = userService.getUserByUsername(username);
        cartService.removeItemFromCart(user, productId);
    }

    /**
     * カート内の商品を更新する
     * @param productId 更新する商品Id
     * @param quantity 商品数
     * @param principal ユーザー情報
     */
    @PutMapping("/update")
    public void updateItemQuantity(@RequestParam Long productId, @RequestParam int quantity, Principal principal) {
    	String username = principal.getName();
    	User user = userService.getUserByUsername(username);
        cartService.updateItemQuantity(user, productId, quantity);
    }
}
