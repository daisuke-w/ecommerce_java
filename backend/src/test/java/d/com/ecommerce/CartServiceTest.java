package d.com.ecommerce;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import d.com.ecommerce.entity.CartItem;
import d.com.ecommerce.entity.Product;
import d.com.ecommerce.entity.User;
import d.com.ecommerce.repository.CartItemRepository;
import d.com.ecommerce.repository.ProductRepository;
import d.com.ecommerce.service.CartService;

public class CartServiceTest {
	
	@Mock
	private CartItemRepository cartItemRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private CartService cartService;
	
	private User user;
	private Product product;
	private CartItem cartItem;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		
		user = new User();
		user.setId(1L);
		user.setUsername("testuser");
		
		product = new Product();
		product.setId(1L);
		product.setName("Test Product");
		product.setPrice(100.0);
		product.setDescription("Description of Test Product");
		
		cartItem = new CartItem();
		cartItem.setId(1L);
		cartItem.setUser(user);
		cartItem.setProduct(product);
		cartItem.setQuantity(1);
	}
	
	@Test
	public void testGetCartItems() {
		given(cartItemRepository.findByUser(user)).willReturn(Arrays.asList(cartItem));
		
		List<CartItem> result = cartService.getCartItems(user);
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(cartItem, result.get(0));
	}
	
	@Test
	public void testAddItemToCart_NewItem() {
		given(productRepository.findById(1L)).willReturn(Optional.of(product));
		given(cartItemRepository.findByUserAndProduct(user, product)).willReturn(Optional.empty());
		
		cartService.addItemToCart(user, 1L, 2);
		
		verify(cartItemRepository, times(1)).save(argThat(cartItem -> {
			return cartItem.getUser().equals(user) &&
				   cartItem.getProduct().equals(product) &&
				   cartItem.getQuantity() == 2;
		}));
	}
	
	@Test
	public void testAddItemToCart_ExistingItem() {
		given(productRepository.findById(1L)).willReturn(Optional.of(product));
		given(cartItemRepository.findByUserAndProduct(user, product)).willReturn(Optional.of(cartItem));
		
		cartService.addItemToCart(user, 1L, 2);
		
		verify(cartItemRepository, times(1)).save(argThat(cartItem -> {
			return cartItem.getUser().equals(user) &&
				   cartItem.getProduct().equals(product) &&
				   cartItem.getQuantity() == 3; // Existing quantity 1 + added quantity 2
		}));
	}
	
	@Test
	public void testRemoveItemFromCart() {
		given(productRepository.findById(1L)).willReturn(Optional.of(product));
		given(cartItemRepository.findByUserAndProduct(user, product)).willReturn(Optional.of(cartItem));
		
		cartService.removeItemFromCart(user, 1L);
		
		verify(cartItemRepository, times(1)).delete(cartItem);
	}
	
	@Test
	public void testUpdateItemQuantity_Update() {
		given(productRepository.findById(1L)).willReturn(Optional.of(product));
		given(cartItemRepository.findByUserAndProduct(user, product)).willReturn(Optional.of(cartItem));
		
		cartService.updateItemQuantity(user, 1L, 5);
		
		verify(cartItemRepository, times(1)).save(argThat(cartItem -> {
			return cartItem.getUser().equals(user) &&
				   cartItem.getProduct().equals(product) &&
				   cartItem.getQuantity() == 5;
		}));
	}
	
	@Test
	public void testUpdateItemQuantity_Delete() {
		given(productRepository.findById(1L)).willReturn(Optional.of(product));
		given(cartItemRepository.findByUserAndProduct(user, product)).willReturn(Optional.of(cartItem));
		
		cartService.updateItemQuantity(user, 1L, 0);
		
		verify(cartItemRepository, times(1)).delete(cartItem);
	}
	
	@Test
	public void testAddItemToCart_ProductNotFound() {
		given(productRepository.findById(1L)).willReturn(Optional.empty());
		
		assertThrows(RuntimeException.class, () -> {
			cartService.addItemToCart(user, 1L, 2);
		});
	}
	
	@Test
	public void testRemoveItemFromCart_ProductNotFound() {
		given(productRepository.findById(1L)).willReturn(Optional.empty());
		
		assertThrows(RuntimeException.class, () -> {
			cartService.removeItemFromCart(user, 1L);
		});
	}
	
	@Test
	public void testUpdateItemQuantity_ProductNotFound() {
		given(productRepository.findById(1L)).willReturn(Optional.empty());
		
		assertThrows(RuntimeException.class, () -> {
			cartService.updateItemQuantity(user, 1L, 5);
		});
	}
	
	@Test
	public void testUpdateItemQuantity_CartItemNotFound() {
		given(productRepository.findById(1L)).willReturn(Optional.of(product));
		given(cartItemRepository.findByUserAndProduct(user, product)).willReturn(Optional.empty());
		
		assertThrows(RuntimeException.class, () -> {
			cartService.updateItemQuantity(user, 1L, 5);
		});
	}
}
