package in.neelesh.online.shopping.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import in.neelesh.online.shopping.dto.PaymentEvent;
import in.neelesh.online.shopping.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentEventProducer {

	private final KafkaTemplate<String, PaymentEvent> paymentSuccess;

	@Async
	public void sendPaymentEvent(PaymentEvent event) {
		log.info("✅ Sending payment success event for orderId: {}", event.getOrderId());
		Message<PaymentEvent> message = MessageBuilder.withPayload(event)
				.setHeader(KafkaHeaders.TOPIC, Constants.PAYMENT_SUCCESS_TOPIC).build();
		paymentSuccess.send(message);
		log.info("📤 Payment event sent: {}", event);
	}

}
