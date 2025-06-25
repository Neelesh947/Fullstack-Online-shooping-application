package in.neelesh.online.shopping.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import in.neelesh.online.shopping.dto.PaymentRequestDto;
import in.neelesh.online.shopping.dto.PaymentResponseDto;
import in.neelesh.online.shopping.entity.Payment;
import in.neelesh.online.shopping.enums.OrderStatus;
import in.neelesh.online.shopping.enums.PaymentStatus;
import in.neelesh.online.shopping.repository.PaymentRepository;
import in.neelesh.online.shopping.util.OrderClientUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;
	private final OrderClientUtils orderClientUtils;

	@Override
	public PaymentResponseDto processPayment(@Valid PaymentRequestDto request, String realm) {
		Payment payment = Payment.builder().orderId(request.orderId()).customerId(request.customerId())
				.amount(request.amount()).paymentMethod(request.method()).transactionId(UUID.randomUUID().toString())
				.paymentStatus(PaymentStatus.SUCCESS).build();

		Payment saved = paymentRepository.save(payment);
		orderClientUtils.updateOrderStatus(realm, request.orderId(), request.customerId(), OrderStatus.PLACED);
		return new PaymentResponseDto(saved);
	}

	@Override
	public PaymentResponseDto getPaymentStatus(String orderId) {
		Payment payment = paymentRepository.findByOrderId(orderId)
				.orElseThrow(() -> new RuntimeException("Payment not found"));
		return new PaymentResponseDto(payment);
	}
}
