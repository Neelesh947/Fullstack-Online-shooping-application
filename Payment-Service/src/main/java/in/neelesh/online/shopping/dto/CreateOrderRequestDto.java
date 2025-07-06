package in.neelesh.online.shopping.dto;

public record CreateOrderRequestDto(String customerId, String cartId, Integer amountInPaise) {
}
