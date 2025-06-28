package in.neelesh.online.shopping.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import in.neelesh.online.shopping.dto.PaymentRequestDto;
import in.neelesh.online.shopping.dto.PaymentResponseDto;
import in.neelesh.online.shopping.entity.Payment;
import in.neelesh.online.shopping.entity.PaymentCardInfo;
import in.neelesh.online.shopping.entity.PaymentNetBankingInfo;
import in.neelesh.online.shopping.entity.PaymentUpiInfo;
import in.neelesh.online.shopping.entity.PaymentWalletInfo;
import in.neelesh.online.shopping.enums.OrderStatus;
import in.neelesh.online.shopping.enums.PaymentStatus;
import in.neelesh.online.shopping.repository.CardInfoRepository;
import in.neelesh.online.shopping.repository.NetBankingInfoRepository;
import in.neelesh.online.shopping.repository.PaymentRepository;
import in.neelesh.online.shopping.repository.UpiInfoRepository;
import in.neelesh.online.shopping.repository.WalletInfoRepository;
import in.neelesh.online.shopping.util.NotificationClient;
import in.neelesh.online.shopping.util.OTPStore;
import in.neelesh.online.shopping.util.OrderClientUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;
	private final OrderClientUtils orderClientUtils;
	private final CardInfoRepository cardInfoRepository;
	private final UpiInfoRepository upiInfoRepository;
	private final WalletInfoRepository walletInfoRepository;
	private final NetBankingInfoRepository netBankingInfoRepository;

	private final OTPStore otpStore;
	private final NotificationClient notificationClient;

	@Override
	public PaymentResponseDto processPayment(@Valid PaymentRequestDto request, String realm) {

		String transactionId = UUID.randomUUID().toString();
		PaymentStatus paymentStatus;
		String message;

		switch (request.method()) {
		case CARD -> {
			if (!isValidCardRequest(request)) {
				return failureResponse(request.orderId(), "Invalid card details or OTP");
			}

			if (request.otp() == null || request.otp().isBlank()) {
				String otp = generateOtp();
				otpStore.saveOtp(request.customerId(), otp, 300);
				notificationClient.sendOtp(request.customerId(), otp, realm);
				return failureResponse(request.orderId(), "OTP sent to registered contact");
			}

			if (!otpStore.isValidOtp(request.customerId(), request.otp())) {
				return failureResponse(request.orderId(), "Invalid or expired OTP");
			}

			saveCardInfo(request);
			paymentStatus = PaymentStatus.SUCCESS;
			message = "Card payment processed successfully";
		}
		case UPI -> {
			if (!isValidUpiRequest(request)) {
				return failureResponse(request.orderId(), "Invalid UPI ID");
			}
			saveUpiInfo(request);
			paymentStatus = PaymentStatus.SUCCESS;
			message = "UPI payment processed successfully";
		}
		case WALLET -> {
			if (!isValidWalletRequest(request)) {
				return failureResponse(request.orderId(), "Invalid wallet details");
			}
			saveWalletInfo(request);
			paymentStatus = PaymentStatus.SUCCESS;
			message = "Wallet payment processed successfully";
		}
		case NET_BANKING -> {
			if (!isValidNetBankingRequest(request)) {
				return failureResponse(request.orderId(), "Invalid net banking details");
			}
			saveNetBankingInfo(request);
			paymentStatus = PaymentStatus.SUCCESS;
			message = "Net banking payment processed successfully";
		}
		default -> throw new IllegalArgumentException("Unexpected payment method: " + request.method());
		}

		Payment payment = Payment.builder().orderId(request.orderId()).customerId(request.customerId())
				.amount(request.amount()).paymentMethod(request.method()).paymentStatus(paymentStatus)
				.transactionId(transactionId).build();

		paymentRepository.save(payment);

		orderClientUtils.updateOrderStatus(realm, request.orderId(), request.customerId(), OrderStatus.PLACED);

		return new PaymentResponseDto(request.orderId(), transactionId, request.customerId(), request.amount(),
				request.method(), paymentStatus, message);
	}

	@Override
	public PaymentResponseDto getPaymentStatus(String orderId) {
		Payment payment = paymentRepository.findByOrderId(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

		return new PaymentResponseDto(payment.getOrderId(), payment.getTransactionId(), payment.getCustomerId(),
				payment.getAmount(), payment.getPaymentMethod(), payment.getPaymentStatus(),
				"Payment status retrieved successfully");
	}

	// generate OTP
	private String generateOtp() {
		return String.valueOf(100000 + (int) (Math.random() * 900000));
	}

	// Validation helpers
	private boolean isValidCardRequest(PaymentRequestDto req) {
		return req.cardNumber() != null && req.cardNumber().length() >= 12 && req.expiryMonth() != null
				&& req.expiryYear() != null && req.cvv() != null && req.cvv().length() >= 3 && req.otp() != null
				&& req.otp().equals("123456");
	}

	private boolean isValidUpiRequest(PaymentRequestDto req) {
		return req.upiId() != null && req.upiId().contains("@");
	}

	private boolean isValidWalletRequest(PaymentRequestDto req) {
		return req.walletProvider() != null && !req.walletProvider().isBlank() && req.walletMobileNumber() != null
				&& !req.walletMobileNumber().isBlank();
	}

	private boolean isValidNetBankingRequest(PaymentRequestDto req) {
		return req.bankName() != null && !req.bankName().isBlank() && req.accountNumber() != null
				&& !req.accountNumber().isBlank() && req.ifscCode() != null && !req.ifscCode().isBlank();
	}

	// Save payment details for each method
	private void saveCardInfo(PaymentRequestDto req) {
		String maskedCard = maskCardNumber(req.cardNumber());
		String cardType = req.cardNumber().startsWith("4") ? "Visa" : "MasterCard";

		PaymentCardInfo cardInfo = PaymentCardInfo.builder().customerId(req.customerId()).maskedCardNumber(maskedCard)
				.expiryMonth(req.expiryMonth()).expiryYear(req.expiryYear()).cardType(cardType).build();

		cardInfoRepository.save(cardInfo);
	}

	private void saveUpiInfo(PaymentRequestDto req) {
		PaymentUpiInfo upiInfo = PaymentUpiInfo.builder().customerId(req.customerId()).upiId(req.upiId()).build();

		upiInfoRepository.save(upiInfo);
	}

	private void saveWalletInfo(PaymentRequestDto req) {
		PaymentWalletInfo walletInfo = PaymentWalletInfo.builder().customerId(req.customerId())
				.provider(req.walletProvider()).mobileNumber(req.walletMobileNumber()).build();

		walletInfoRepository.save(walletInfo);
	}

	private void saveNetBankingInfo(PaymentRequestDto req) {
		PaymentNetBankingInfo netBankingInfo = PaymentNetBankingInfo.builder().customerId(req.customerId())
				.bankName(req.bankName()).accountNumber(req.accountNumber()).ifscCode(req.ifscCode()).build();

		netBankingInfoRepository.save(netBankingInfo);
	}

	private PaymentResponseDto failureResponse(String orderId, String message) {
		return new PaymentResponseDto(orderId, null, null, 0, null, PaymentStatus.FAILED, message);
	}

	// Utility to mask card number (only last 4 digits visible)
	private String maskCardNumber(String cardNumber) {
		if (cardNumber == null || cardNumber.length() < 4)
			return cardNumber;
		int length = cardNumber.length();
		return "*".repeat(length - 4) + cardNumber.substring(length - 4);
	}
}
