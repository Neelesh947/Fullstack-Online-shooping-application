package in.neelesh.online.shopping.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(String orderId, String customerId, List<OrderItemResponseDto> items,
		String shippingAddress, String status, LocalDateTime orderDate, LocalDateTime updatedDate) {

}
