package d.com.ecommerce.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import d.com.ecommerce.entity.CustomerOrder;
import d.com.ecommerce.entity.User;
import d.com.ecommerce.service.OrderService;
import d.com.ecommerce.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;
	
	@PostMapping
	public CustomerOrder createOrder(Principal principal) {
		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		return orderService.createOrder(user);
	}
	
	@GetMapping("/{orderId}")
	public CustomerOrder getOrderById(@PathVariable Long orderId) {
		return orderService.getOrderById(orderId);
	}
}
