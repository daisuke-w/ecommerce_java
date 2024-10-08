package d.com.ecommerce.dto;

import lombok.Data;

@Data
public class CartRequest {
	private Long productId;
	private int quantity;
}
