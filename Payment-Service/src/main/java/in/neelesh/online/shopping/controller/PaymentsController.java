package in.neelesh.online.shopping.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.neelesh.online.shopping.dto.CreateOrderRequestDto;
import in.neelesh.online.shopping.dto.CreateOrderResponseDto;
import in.neelesh.online.shopping.dto.PaymentRequestDTOs;
import in.neelesh.online.shopping.dto.PaymentResponseDTOs;
import in.neelesh.online.shopping.service.PaymentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/{realm}/api/v1/payment")
@RequiredArgsConstructor
@Validated
public class PaymentsController {

	private final PaymentsService paymentsService;

	@PostMapping("/create")
	public ResponseEntity<CreateOrderResponseDto> createpaymentOrder(
			@RequestBody @Valid CreateOrderRequestDto request) {
		return ResponseEntity.ok(paymentsService.createPaymentOrder(request));
	}

	@PostMapping("/qr")
	public ResponseEntity<PaymentResponseDTOs> createQRCode(@RequestBody PaymentRequestDTOs paymentResponseDTOs) {
		PaymentResponseDTOs response = paymentsService.genearteQrCodePayemnt(paymentResponseDTOs);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/verify")
	public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> paymentData) {
		return paymentsService.verifyPayment(paymentData);
	}
}
