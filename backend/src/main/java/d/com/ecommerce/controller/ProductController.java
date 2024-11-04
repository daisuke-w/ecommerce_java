package d.com.ecommerce.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import d.com.ecommerce.entity.Product;
import d.com.ecommerce.entity.User;
import d.com.ecommerce.service.ProductService;
import d.com.ecommerce.service.UserService;

/**
 * 商品情報を管理するエンドポイントを提供する
 * 商品に関連するCRUD操作（作成、取得、更新、削除）を処理する
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;

	/**
	 * すべての商品を取得する
	 * 
	 * @return 商品のリスト
	 */
	@GetMapping
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}
	
	/**
	 * 指定されたIDの商品を取得する
	 * 
	 * @param id 取得したい商品のID
	 * @return 商品が存在する場合は200 OKレスポンスと商品情報を返し、
	 *         存在しない場合は404 Not Foundを返す
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		Optional<Product> product = productService.getProductById(id);
		return product.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	/**
	 * 新しい商品を作成する
	 * 
	 * @param product 作成する商品の情報
	 * @return 201 Createdレスポンスと作成された商品の情報を返す
	 */
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody Product product, Principal principal) {
	    String username = principal.getName();
	    User user = userService.getUserByUsername(username);
	    product.setUser_id(user.getId());
	    Product createdProduct = productService.createProduct(product);
	    return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	}
	
	/**
	 * 指定されたIDの商品情報を更新する
	 * 
	 * @param id 更新する商品のID
	 * @param productDetails 更新後の商品情報
	 * @return 200 OKレスポンスと更新された商品の情報を返す
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
		return ResponseEntity.ok(productService.updateProduct(id, productDetails));
	}

	/**
	 * 指定されたIDの商品を削除する
	 * 
	 * @param id 削除する商品のID
	 * @return 204 No Contentレスポンスを返す
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
}

