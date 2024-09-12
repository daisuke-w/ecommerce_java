package d.com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import d.com.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
