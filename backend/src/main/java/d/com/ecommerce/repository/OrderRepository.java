package d.com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import d.com.ecommerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
