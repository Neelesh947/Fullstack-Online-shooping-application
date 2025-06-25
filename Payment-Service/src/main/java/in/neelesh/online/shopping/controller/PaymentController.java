package in.neelesh.online.shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.neelesh.online.shopping.dto.PaymentRequestDto;
import in.neelesh.online.shopping.dto.PaymentResponseDto;
import in.neelesh.online.shopping.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{realm}/payment")
public class PaymentController {
	
	private final PaymentService paymentService;
	
	@PostMapping
    public ResponseEntity<PaymentResponseDto> pay(@RequestBody @Valid PaymentRequestDto request, @PathVariable String realm) {
        return ResponseEntity.ok(paymentService.processPayment(request,realm));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentResponseDto> getPaymentStatus(@PathVariable String orderId) {
        return ResponseEntity.ok(paymentService.getPaymentStatus(orderId));
    }

}
