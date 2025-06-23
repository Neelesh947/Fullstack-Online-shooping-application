package in.neelesh.online.shopping.dto;

import java.util.List;

public record OrderCreateDto(String customerId, List<OrderItemDto> items, String shippingAddress) {
}
