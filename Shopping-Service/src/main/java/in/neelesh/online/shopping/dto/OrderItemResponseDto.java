package in.neelesh.online.shopping.dto;

public record OrderItemResponseDto(String productId, String productName, int quantity, double price) {
}
