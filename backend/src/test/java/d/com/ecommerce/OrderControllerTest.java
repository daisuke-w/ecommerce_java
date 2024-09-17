package d.com.ecommerce;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import d.com.ecommerce.controller.OrderController;
import d.com.ecommerce.entity.Order;
import d.com.ecommerce.entity.User;
import d.com.ecommerce.service.OrderService;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
	
	@MockBean
	private OrderService orderService;
	
	@InjectMocks
	private OrderController orderController;
	
	private MockMvc mockMvc;
	
	@Mock
	private User user;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
		
		given(user.getId()).willReturn(1L);
		given(user.getUsername()).willReturn("testuser");
	}
	
	@Test
	public void testCreateOrder() throws Exception {
		Order order = new Order();
		order.setId(1L);
		order.setUser(user);
		order.setOrderDate(LocalDateTime.now());
		order.setTotalAmount(100.0);
		
		given(orderService.createOrder(user)).willReturn(order);
		
		mockMvc.perform(post("/orders"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testGetOrderById() throws Exception {
		Order order = new Order();
		order.setId(1L);
		order.setUser(user);
		order.setOrderDate(LocalDateTime.now());
		order.setTotalAmount(100.0);
		
		given(orderService.getOrderById(1L)).willReturn(order);
		
		mockMvc.perform(get("/orders/1"))
			.andExpect(status().isOk());
	}
}
