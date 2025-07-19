package in.neelesh.online.shopping.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import in.neelesh.online.shopping.dto.PaymentEvent;
import in.neelesh.online.shopping.service.ProductService;
import in.neelesh.online.shopping.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventConsumer {

	private final ProductService productService;

	@KafkaListener(topics = Constants.PAYMENT_SUCCESS_TOPIC, groupId = "shopping-group")
	public void consumePaymentEvent(PaymentEvent event) {
		log.info("📥 Received PaymentEvent from Kafka: {}", event);

		if (event.isPaymentSuccess() && event.getCartId() != null) {
			productService.reduceProductQuantity(event.getCartId());
		} else {
			log.warn("❌ Ignoring event — payment failed or cart ID missing: {}", event);
		}

	}
}
