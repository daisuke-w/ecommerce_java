package d.com.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import d.com.ecommerce.entity.CartItem;
import d.com.ecommerce.entity.Order;
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
	public Order createOrder(User user) {
		List<CartItem> cartItems = cartItemRepository.findByUser(user);
		if (cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}
		
		Order order = new Order();
		order.setUser(user);
		order.setOrderDate(LocalDateTime.now());
		order.setTotalAmount(calculateTotalAmount(cartItems));
		
		List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
			return orderItem;
		}).collect(Collectors.toList());
		
		order.setOrderItems(orderItems);
		
		Order savedOrder = orderRepository.save(order);
		
		cartItemRepository.deleteAll(cartItems);
		
		return savedOrder;
	}
	
	public Order getOrderById(Long orderId) {
	    return orderRepository.findById(orderId)
	            .orElseThrow(() -> new RuntimeException("Order not found"));
	}
	
	private Double calculateTotalAmount(List<CartItem> cartItems) {
		return cartItems.stream()
				.mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
				.sum();
	}
}
