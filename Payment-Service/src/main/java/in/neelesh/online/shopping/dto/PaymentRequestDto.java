package in.neelesh.online.shopping.dto;

import in.neelesh.online.shopping.enums.PaymentMethod;

public record PaymentRequestDto(String orderId, String customerId, double amount, PaymentMethod method,
		// Card
		String cardNumber, String expiryMonth, String expiryYear, String cvv, String otp,

		// UPI
		String upiId,

		// Wallet
		String walletProvider, String walletMobileNumber,

		// Net Banking
		String bankName, String accountNumber, String ifscCode) {

}
