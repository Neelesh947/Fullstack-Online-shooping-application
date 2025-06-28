package in.neelesh.online.shopping.dto;

public record OrderCancelledEvent(String orderId, String customerId, String reason) {

}
