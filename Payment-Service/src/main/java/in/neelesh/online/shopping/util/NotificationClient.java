package in.neelesh.online.shopping.util;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationClient {

	private final RestTemplate restTemplate;

	// Use https and double slash after http: (correct the URL)
	private static final String NOTIFICATION_SERVICE_URL = "http://localhost:1235";

	/**
	 * Asynchronously send OTP notification to Notification Service.
	 *
	 * @param customerId Customer identifier
	 * @param otp        One-Time Password string
	 * @param realm      Realm or tenant identifier
	 * @return CompletableFuture<Void>
	 */
	@Async
	public CompletableFuture<Void> sendOtp(String customerId, String otp, String realm) {
		String url = String.format("%s/%s/notification/send-otp", NOTIFICATION_SERVICE_URL, realm);

		Map<String, String> payload = Map.of("customerId", customerId, "otp", otp);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(payload, headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

			if (!response.getStatusCode().is2xxSuccessful()) {
				log.error("Failed to send OTP notification. Status: {}, Response Body: {}", response.getStatusCode(),
						response.getBody());
				throw new RuntimeException("Failed to send OTP: " + response.getBody());
			} else {
				log.info("OTP sent successfully to customerId={} in realm={}", customerId, realm);
			}
		} catch (Exception e) {
			log.error("Exception occurred while sending OTP notification to customerId={} in realm={}: {}", customerId,
					realm, e.getMessage(), e);
			// Optionally rethrow or handle accordingly
		}

		return CompletableFuture.completedFuture(null);
	}
}