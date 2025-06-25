package in.neelesh.online.shopping.service;

import in.neelesh.online.shopping.dto.PaymentRequestDto;
import in.neelesh.online.shopping.dto.PaymentResponseDto;
import jakarta.validation.Valid;

public interface PaymentService {

	PaymentResponseDto processPayment(@Valid PaymentRequestDto request, String realm);

	PaymentResponseDto getPaymentStatus(String orderId);

}
