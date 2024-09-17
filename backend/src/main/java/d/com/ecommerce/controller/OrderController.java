package d.com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import d.com.ecommerce.entity.Order;
import d.com.ecommerce.entity.User;
import d.com.ecommerce.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public Order createOrder(@AuthenticationPrincipal User user) {
		return orderService.createOrder(user);
	}
	
	@GetMapping("/{orderId}")
	public Order getOrderById(@PathVariable Long orderId) {
		return orderService.getOrderById(orderId);
	}
}
