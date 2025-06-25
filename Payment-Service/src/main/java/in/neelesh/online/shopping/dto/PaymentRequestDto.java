package in.neelesh.online.shopping.dto;

import in.neelesh.online.shopping.enums.PaymentMethod;

public record PaymentRequestDto(String orderId, String customerId, double amount, PaymentMethod method) {

}
