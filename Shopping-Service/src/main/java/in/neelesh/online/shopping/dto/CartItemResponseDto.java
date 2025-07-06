package in.neelesh.online.shopping.dto;

public record CartItemResponseDto(String productId, String productName, double price, int quantity, double totalPrice,
		String imageId) {
}
