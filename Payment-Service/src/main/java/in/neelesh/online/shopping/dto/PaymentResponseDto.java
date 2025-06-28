package in.neelesh.online.shopping.dto;

import in.neelesh.online.shopping.enums.PaymentMethod;
import in.neelesh.online.shopping.enums.PaymentStatus;

public record PaymentResponseDto(String orderId, String transactionId, String customerId, double amount,
		PaymentMethod method, PaymentStatus status, String message) {

}
