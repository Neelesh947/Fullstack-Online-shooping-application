package in.neelesh.online.shopping.dto;

import in.neelesh.online.shopping.entity.Payment;

public record PaymentResponseDto(String orderId, String transactionId, double amount, String status) {

	public PaymentResponseDto(Payment payment) {
		this(payment.getOrderId(), payment.getTransactionId(), payment.getAmount(), payment.getPaymentStatus().name());
	}
}
