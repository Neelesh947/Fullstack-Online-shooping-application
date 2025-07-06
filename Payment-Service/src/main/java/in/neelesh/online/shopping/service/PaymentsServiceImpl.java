package in.neelesh.online.shopping.service;

import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import in.neelesh.online.shopping.dto.CreateOrderRequestDto;
import in.neelesh.online.shopping.dto.CreateOrderResponseDto;
import in.neelesh.online.shopping.dto.PaymentRequestDTOs;
import in.neelesh.online.shopping.dto.PaymentResponseDTOs;
import in.neelesh.online.shopping.entity.PaymentRecords;
import in.neelesh.online.shopping.enums.PaymentGateway;
import in.neelesh.online.shopping.enums.PaymentMode;
import in.neelesh.online.shopping.enums.PaymentStatus;
import in.neelesh.online.shopping.repository.PaymentRecordsRepository;
import in.neelesh.online.shopping.util.Constants;
import in.neelesh.online.shopping.util.OrderPayload;
import in.neelesh.online.shopping.util.PaymentPayloadBuilder;
import in.neelesh.online.shopping.util.RazorPaySignatureVerify;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentsServiceImpl implements PaymentsService {

	@Value("${razorpay.key-id}")
	private String razorpayKey;

	@Value("${razorpay.key-secret}")
	private String razorpaySecret;

	private RazorpayClient razorpayClient;

	private final PaymentRecordsRepository paymentRecordsRepository;
	private final PaymentPayloadBuilder paymentPayloadBuilder;

	@PostConstruct
	public void init() throws Exception {
		razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);
	}

	public CreateOrderResponseDto createPaymentOrder(@Valid CreateOrderRequestDto request) {
		try {
			JSONObject orderPayload = OrderPayload.buildPayloadForCreateOrder(request.amountInPaise(), Constants.INR,
					Constants.RECEIPT_ID + System.currentTimeMillis(), 1);
			Order order = razorpayClient.orders.create(orderPayload);

			PaymentRecords record = PaymentRecords.builder().paymentGateway(PaymentGateway.RAZORPAY)
					.amountInPaise(request.amountInPaise()).cartId(request.cartId()).userId(request.customerId())
					.paymentStatus(PaymentStatus.CREATED)
					.paymentMode(PaymentMode.valueOf(request.paymentMode().toUpperCase()))
					.paymentReferenceUrl(order.get("id")).build();

			paymentRecordsRepository.save(record);

			return new CreateOrderResponseDto(order.get("id"), razorpayKey, order.get("amount"), order.get("currency"));
		} catch (Exception e) {
			throw new RuntimeException("Failed to create Razorpay order: " + e.getMessage());
		}
	}

	@Override
	public PaymentResponseDTOs genearteQrCodePayemnt(PaymentRequestDTOs dto) {
		try {
			JSONObject payload = paymentPayloadBuilder.buildQrCodePayload(dto, true);

			String url = "https://api.razorpay.com/v1/payments/qr_codes";
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			String auth = razorpayKey + ":" + razorpaySecret;
			String encodedAuth = Base64Utils.encodeToString(auth.getBytes());
			headers.add("Authorization", "Basic " + encodedAuth);

			HttpEntity<String> entity = new HttpEntity<>(payload.toString(), headers);

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
				JSONObject qrResponse = new JSONObject(response.getBody());
				String paymentReferenceUrl = qrResponse.getString("short_url");
				String qrCodeUrl = qrResponse.getString("image_url");
				String upiRequestId = qrResponse.optString("upi_request_id", null);

				return new PaymentResponseDTOs(paymentReferenceUrl, qrCodeUrl, upiRequestId);
			} else {
				throw new RuntimeException("Failed to create Razorpay QR Code: HTTP " + response.getStatusCode());
			}

		} catch (Exception e) {
			throw new RuntimeException("Failed to create Razorpay QR Code: " + e.getMessage(), e);
		}
	}

	@Override
	public ResponseEntity<?> verifyPayment(Map<String, String> paymentData) {
		try {
			String razorpayOrderId = paymentData.get("razorpay_order_id");
			String razorpayPaymentId = paymentData.get("razorpay_payment_id");
			String razorpaySignature = paymentData.get("razorpay_signature");

			String secret = razorpaySecret;

			try {
				String data = razorpayOrderId + "|" + razorpayPaymentId;
		        String generatedSignature = hmacSha256(data, secret);
		        
		        if (generatedSignature.equals(razorpaySignature)) {
		            return ResponseEntity.ok(Map.of("success", true));
		        } else {
		            return ResponseEntity.ok(Map.of("success", false));
		        }		        
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(Map.of("status", "error", "message", "Internal server error"));
		}

	}

	private String hmacSha256(String data, String key) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes()));
	}
}
