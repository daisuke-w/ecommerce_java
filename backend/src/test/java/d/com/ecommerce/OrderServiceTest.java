package d.com.ecommerce;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import d.com.ecommerce.entity.CartItem;
import d.com.ecommerce.entity.Order;
import d.com.ecommerce.entity.Product;
import d.com.ecommerce.entity.User;
import d.com.ecommerce.repository.CartItemRepository;
import d.com.ecommerce.repository.OrderRepository;
import d.com.ecommerce.service.OrderService;

public class OrderServiceTest {
	@Mock
	private OrderRepository orderRepository;

	@Mock
	private CartItemRepository cartItemRepository;

	@InjectMocks
	private OrderService orderService;

	@Mock
	private User user;

	@Mock
	private Product product;

	@Mock
	private CartItem cartItem;

	@BeforeEach
	public void setUp() {
	    MockitoAnnotations.openMocks(this);

	    // モックのUser設定
	    given(user.getId()).willReturn(1L);
	    given(user.getUsername()).willReturn("testuser");

	    // モックのProduct設定
	    given(product.getId()).willReturn(1L);
	    given(product.getName()).willReturn("Test Product");
	    given(product.getPrice()).willReturn(100.0);

	    // モックのCartItem設定
	    given(cartItem.getUser()).willReturn(user);
	    given(cartItem.getProduct()).willReturn(product);
	    given(cartItem.getQuantity()).willReturn(1);
	}
	
	@Test
	public void testCreateOrder() {
		given(cartItemRepository.findByUser(user)).willReturn(Arrays.asList(cartItem));
		given(orderRepository.save(any(Order.class))).willAnswer(invocation -> invocation.getArgument(0));

		Order order = orderService.createOrder(user);

		assertNotNull(order);
		assertEquals(100.0, order.getTotalAmount());
		Mockito.verify(cartItemRepository, times(1)).deleteAll(any());
	}
	
	@Test
	public void testGetOrderById() {
	    Order order = new Order();
	    order.setId(1L);
	    order.setUser(user);
	    order.setOrderDate(LocalDateTime.now());
	    order.setTotalAmount(100.0);

	    given(orderRepository.findById(1L)).willReturn(Optional.of(order));

	    Order foundOrder = orderService.getOrderById(1L);

	    assertNotNull(foundOrder);
	    assertEquals(1L, foundOrder.getId());
	    assertEquals(100.0, foundOrder.getTotalAmount());
	}
}