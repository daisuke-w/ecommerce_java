package d.com.ecommerce;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import d.com.ecommerce.model.Product;
import d.com.ecommerce.repository.ProductRepository;
import d.com.ecommerce.service.ProductService;

public class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

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
	public void testGetAllProducts() {
		Product product1 = new Product();
		product1.setId(1L);
		product1.setName("Product 1");
		product1.setPrice(100.0);
		product1.setDescription("Description of Product 1");

		Product product2 = new Product();
		product2.setId(2L);
		product2.setName("Product 2");
		product2.setPrice(150.0);
		product2.setDescription("Description of Product 2");

		List<Product> products = Arrays.asList(product1, product2);

		given(productRepository.findAll()).willReturn(products);

		List<Product> allProducts = productService.getAllProducts();

		assertNotNull(allProducts);
		assertEquals(2, allProducts.size());
		assertEquals("Product 1", allProducts.get(0).getName());
		assertEquals("Product 2", allProducts.get(1).getName());
	}

	@Test
	public void testGetProductById() {
		given(productRepository.findById(1L)).willReturn(Optional.of(product));

		Optional<Product> foundProduct = productService.getProductById(1L);
		assertTrue(foundProduct.isPresent());
		assertEquals("Test Product", foundProduct.get().getName());
	}

	@Test
	public void testCreateProduct() {
		given(productRepository.save(product)).willReturn(product);

		Product createdProduct = productService.createProduct(product);
		assertEquals("Test Product", createdProduct.getName());
	}

	@Test
	public void testUpdateProduct() {
		Product existingProduct = new Product();
		existingProduct.setId(1L);
		existingProduct.setName("Old Product");
		existingProduct.setPrice(100.0);
		existingProduct.setDescription("Description of Old Product");

		given(productRepository.findById(1L)).willReturn(Optional.of(existingProduct));
		given(productRepository.save(existingProduct)).willReturn(existingProduct);

		existingProduct.setName("Test Product");
		existingProduct.setPrice(150.0);

		Product updatedProduct = productService.updateProduct(1L, existingProduct);

		assertEquals("Test Product", updatedProduct.getName());
	}

	@Test
	public void testDeleteProduct() {
		given(productRepository.existsById(1L)).willReturn(true);
		productService.deleteProduct(1L);
		then(productRepository).should().deleteById(1L);
	}
}
