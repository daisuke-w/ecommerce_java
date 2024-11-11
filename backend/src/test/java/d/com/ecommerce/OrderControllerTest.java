package d.com.ecommerce;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import d.com.ecommerce.config.JwtUtils;
import d.com.ecommerce.controller.OrderController;
import d.com.ecommerce.entity.CustomerOrder;
import d.com.ecommerce.entity.User;
import d.com.ecommerce.service.CustomUserDetailsService;
import d.com.ecommerce.service.OrderService;
import d.com.ecommerce.service.UserService;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserService userService;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private User user;

    private Principal principal;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        principal = mock(Principal.class);
        given(principal.getName()).willReturn("testuser");

        given(user.getId()).willReturn(1L);
        given(user.getUsername()).willReturn("testuser");
    }

    @Test
    public void testCreateOrder() throws Exception {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(1L);
        customerOrder.setUser(user);
        customerOrder.setOrderDate(LocalDateTime.now());
        customerOrder.setTotalAmount(100.0);

        given(userService.getUserByUsername("testuser")).willReturn(user);
        given(orderService.createOrder(user)).willReturn(customerOrder);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
	
	@Test
	public void testGetOrderById() throws Exception {
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setId(1L);
		customerOrder.setUser(user);
		customerOrder.setOrderDate(LocalDateTime.now());
		customerOrder.setTotalAmount(100.0);
		
		given(orderService.getOrderById(1L)).willReturn(customerOrder);
		
		mockMvc.perform(get("/orders/1"))
			.andExpect(status().isOk());
	}
}
