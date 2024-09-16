package d.com.ecommerce;

import static org.mockito.BDDMockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import d.com.ecommerce.controller.CartController;
import d.com.ecommerce.entity.CartItem;
import d.com.ecommerce.entity.Product;
import d.com.ecommerce.entity.User;
import d.com.ecommerce.service.CartService;
import d.com.ecommerce.service.UserService;

@WebMvcTest(CartController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private UserService userService;

    @Mock
    private User user;
    
    @Mock
    private Product product;
    
    @Mock
    private CartItem cartItem;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // カスタムモック設定
        given(user.getId()).willReturn(1L);
        given(user.getUsername()).willReturn("testuser");
        given(product.getId()).willReturn(1L);
        given(product.getName()).willReturn("Test Product");
        given(product.getPrice()).willReturn(100.0);
        given(product.getDescription()).willReturn("Description of Test Product");
        given(cartItem.getId()).willReturn(1L);
        given(cartItem.getUser()).willReturn(user);
        given(cartItem.getProduct()).willReturn(product);
        given(cartItem.getQuantity()).willReturn(1);
    }

    @Test
    public void testGetCartItems() throws Exception {
        given(userService.getUserByUserId(1L)).willReturn(user);
        given(cartService.getCartItems(user)).willReturn(Arrays.asList(cartItem));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cart")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].product.name").value("Test Product"));
    }
    
    @Test
    public void testAddItemToCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart/add")
                .param("userId", "1")
                .param("productId", "1")
                .param("quantity", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRemoveItemFromCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cart/remove")
                .param("userId", "1")
                .param("productId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateItemQuantity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/cart/update")
                .param("userId", "1")
                .param("productId", "1")
                .param("quantity", "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
