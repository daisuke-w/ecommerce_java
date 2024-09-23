package d.com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import d.com.ecommerce.entity.CustomerOrder;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
}
