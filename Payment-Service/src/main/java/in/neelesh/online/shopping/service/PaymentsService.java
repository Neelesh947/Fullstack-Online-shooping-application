package in.neelesh.online.shopping.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import in.neelesh.online.shopping.dto.CreateOrderRequestDto;
import in.neelesh.online.shopping.dto.CreateOrderResponseDto;
import in.neelesh.online.shopping.dto.PaymentRequestDTOs;
import in.neelesh.online.shopping.dto.PaymentResponseDTOs;
import jakarta.validation.Valid;

public interface PaymentsService {

	CreateOrderResponseDto createPaymentOrder(@Valid CreateOrderRequestDto request);

	PaymentResponseDTOs genearteQrCodePayemnt(PaymentRequestDTOs paymentResponseDTOs);

	ResponseEntity<?> verifyPayment(Map<String, String> paymentData);

}
