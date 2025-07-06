package in.neelesh.online.shopping.dto;

public record CreateOrderResponseDto(String razorpayOrderId, String key, Integer amountInPaise, String currency) {
}
