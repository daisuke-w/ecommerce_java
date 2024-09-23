package d.com.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import d.com.ecommerce.entity.CartItem;
import d.com.ecommerce.entity.CustomerOrder;
import d.com.ecommerce.entity.OrderItem;
import d.com.ecommerce.entity.User;
import d.com.ecommerce.repository.CartItemRepository;
import d.com.ecommerce.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Transactional
	public CustomerOrder createOrder(Optional<User> userOpt) {
		User user = userOpt.orElseThrow(() -> new RuntimeException("User not found"));

		List<CartItem> cartItems = cartItemRepository.findByUser(user);
		if (cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}
		
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setUser(user);
		customerOrder.setOrderDate(LocalDateTime.now());
		customerOrder.setTotalAmount(calculateTotalAmount(cartItems));
		
		List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
			OrderItem orderItem = new OrderItem();
			orderItem.setCustomerOrder(customerOrder);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
			return orderItem;
		}).collect(Collectors.toList());
		
		customerOrder.setOrderItems(orderItems);
		
		CustomerOrder savedOrder = orderRepository.save(customerOrder);
		
		cartItemRepository.deleteAll(cartItems);
		
		return savedOrder;
	}
	
	public CustomerOrder getOrderById(Long orderId) {
	    return orderRepository.findById(orderId)
	            .orElseThrow(() -> new RuntimeException("Order not found"));
	}
	
	private Double calculateTotalAmount(List<CartItem> cartItems) {
		return cartItems.stream()
				.mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
				.sum();
	}
}
