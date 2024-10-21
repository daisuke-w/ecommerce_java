package d.com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import d.com.ecommerce.entity.Product;
import d.com.ecommerce.repository.ProductRepository;

/**
 * 商品の取得、作成、更新、削除機能を提供する
 */
@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	/**
	 * 全ての商品を取得する
	 * @return 商品一覧
	 */
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	/**
	 * 商品Idで商品を取得する
	 * @param id 商品Id
	 * @return Idに紐づく商品
	 */
	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}
	
	/**
	 * 商品を出品する
	 * @param product 出品する商品
	 * @return レスポンス200
	 */
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}
	
	/**
	 * 出品した商品を更新する
	 * @param id 更新する商品Id
	 * @param productDetails 更新内容
	 * @return レスポンス200
	 */
	public Product updateProduct(Long id, Product productDetails) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found"));
		product.setName(productDetails.getName());
		product.setDescription(productDetails.getDescription());
		product.setPrice(productDetails.getPrice());
		product.setStock(productDetails.getStock());
		
		return productRepository.save(product);
	}
	
	/**
	 * 出品した商品を削除する
	 * @param id 削除する商品Id
	 */
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
}
