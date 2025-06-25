package in.neelesh.online.shopping.dto;

import java.util.List;

public record CartResponseDto(String cartId, String customerId, List<CartItemResponseDto> items,
		double totalCartPrice) {
}
