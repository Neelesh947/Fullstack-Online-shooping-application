package in.neelesh.online.shopping.dto;

import java.util.List;

public record OrderCreateDto(List<OrderItemDto> items, String shippingAddress) {
}
