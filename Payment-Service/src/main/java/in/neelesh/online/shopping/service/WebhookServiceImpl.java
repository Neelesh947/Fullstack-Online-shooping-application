package in.neelesh.online.shopping.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.neelesh.online.shopping.entity.PaymentRecords;
import in.neelesh.online.shopping.enums.PaymentMode;
import in.neelesh.online.shopping.enums.PaymentStatus;
import in.neelesh.online.shopping.repository.PaymentRecordsRepository;
import in.neelesh.online.shopping.util.RazorPaySignatureVerify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebhookServiceImpl implements WebhookService {

	@Value("${webhook.secret}")
	private String webhookSecret;

	private final ObjectMapper objectMapper;
	private final RazorPaySignatureVerify razorPaySignatureVerify;

	private final PaymentRecordsRepository paymentRecordsRepository;

	@SuppressWarnings("static-access")
	@Override
	@Transactional
	public void processWebhook(String payload, String razorpaySignature) {
		try {
			log.info("Webhook payload: {}", payload);
			log.info("Razorpay signature header: {}", razorpaySignature);

			boolean isValid = razorPaySignatureVerify.isSignatureValid(payload, razorpaySignature, webhookSecret);

			if (!isValid) {
				return;
			}

			JsonNode rootNode = parsePayload(payload);
			String eventType = rootNode.get("event").asText();

			switch (eventType) {
			case "payment.captured":
				handlePaymentCaptured(rootNode);
				break;
			case "payment.failed":
				handlePaymentFailed(rootNode);
				break;
			case "order.paid":
				handleOrderPaid(rootNode);
				break;
			default:
				log.warn("⚠️ Unhandled webhook event type: {}", eventType);
			}
		} catch (Exception e) {
			log.error("🔥 Error processing Razorpay webhook: {}", e.getMessage(), e);
		}
	}

	private JsonNode parsePayload(String payload) throws JsonProcessingException {
		return objectMapper.readTree(payload);
	}

	private void handlePaymentCaptured(JsonNode rootNode) {
		JsonNode entity = rootNode.at("/payload/payment/entity");
		String paymentId = entity.get("id").asText();
		String orderId = entity.get("order_id").asText();
		long amount = entity.get("amount").asLong();
		String method = entity.get("method").asText();
		String currency = entity.get("currency").asText();

		log.info("💰 Payment Captured | paymentId: {}, orderId: {}, amount: {}", paymentId, orderId, amount);

		PaymentRecords paymentRecord = paymentRecordsRepository.findByPaymentReferenceUrl(orderId);
		if (paymentRecord == null) {
			log.warn("Payment record not found for orderId: {}", orderId);
			return;
		}

		paymentRecord.setExternalPaymentId(paymentId);
		paymentRecord.setPaymentStatus(PaymentStatus.SUCCESS);
		paymentRecord.setAmountInPaise((int) amount); // cast as needed
		paymentRecord.setTransactionId(orderId);
		paymentRecord.setPaymentMode(method != null ? mapToPaymentMode(method) : null);

		paymentRecordsRepository.save(paymentRecord);
	}

	private void handlePaymentFailed(JsonNode rootNode) {
		JsonNode entity = rootNode.at("/payload/payment/entity");
		String paymentId = entity.get("id").asText();
		String orderId = entity.get("order_id").asText();
		String errorReason = entity.has("error_description") ? entity.get("error_description").asText() : "Unknown";

		log.warn("❌ Payment Failed | paymentId: {}, reason: {}", paymentId, errorReason);

		PaymentRecords paymentRecord = paymentRecordsRepository.findByTransactionId(orderId);
		if (paymentRecord == null) {
			log.warn("Payment record not found for orderId: {}", orderId);
			return;
		}

		paymentRecord.setExternalPaymentId(paymentId);
		paymentRecord.setPaymentStatus(PaymentStatus.FAILED);

		paymentRecordsRepository.save(paymentRecord);
	}

	private void handleOrderPaid(JsonNode rootNode) {
		JsonNode entity = rootNode.at("/payload/order/entity");
		String razorpayOrderId = entity.get("id").asText();

		log.info("🧾 Order Paid | razorpayOrderId: {}", razorpayOrderId);

		PaymentRecords paymentRecord = paymentRecordsRepository.findByTransactionId(razorpayOrderId);
		if (paymentRecord != null) {
			paymentRecord.setPaymentStatus(PaymentStatus.SUCCESS);
			paymentRecordsRepository.save(paymentRecord);
		} else {
			log.warn("Payment record not found for razorpayOrderId: {}", razorpayOrderId);
		}
	}

	private PaymentMode mapToPaymentMode(String method) {
		try {
			return PaymentMode.valueOf(method.toUpperCase());
		} catch (IllegalArgumentException e) {
			log.warn("Unknown payment method received: {}", method);
			return null;
		}
	}
}
