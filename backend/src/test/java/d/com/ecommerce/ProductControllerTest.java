package d.com.ecommerce;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import d.com.ecommerce.controller.ProductController;
import d.com.ecommerce.model.Product;
import d.com.ecommerce.service.ProductService;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService productService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Product product;
	
	@BeforeEach
	public void setUp() {
	    MockitoAnnotations.openMocks(this);
	
	    product = new Product();
	    product.setId(1L);
	    product.setName("Test Product");
	    product.setPrice(100.0);
	    product.setDescription("Description of Test Product");
	}
	
	@Test
	public void testGetAllProducts() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void testGetProductById() throws Exception {
	    given(productService.getProductById(1L)).willReturn(Optional.of(product));
	
	    mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1"))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Product"));
	}
	
	@Test
	public void testCreateProduct() throws Exception {
	    given(productService.createProduct(product)).willReturn(product);
	
	    mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(product)))
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Product"));
	}
	
	@Test
	public void testUpdateProduct() throws Exception {
	    Product updatedProduct = new Product();
	    updatedProduct.setId(1L);
	    updatedProduct.setName("Updated Product");
	    updatedProduct.setPrice(150.0);
	    updatedProduct.setDescription("Updated Description");
	
	    given(productService.updateProduct(1L, updatedProduct)).willReturn(updatedProduct);
	
	    mockMvc.perform(MockMvcRequestBuilders.put("/api/products/1")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(updatedProduct)))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Product"));
	}
	
	@Test
	public void testDeleteProduct() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/1"))
	            .andExpect(MockMvcResultMatchers.status().isNoContent());
	
	    verify(productService).deleteProduct(1L);
	}
}
