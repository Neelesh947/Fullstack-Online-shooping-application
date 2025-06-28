package in.neelesh.online.shopping.config;

import java.util.*;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import in.neelesh.online.shopping.dto.OrderCancelledEvent;
import in.neelesh.online.shopping.dto.OrderCreatedEvent;
import in.neelesh.online.shopping.dto.ProductChangedEvent;

@Configuration
public class KafkaProducerConfig {

	private <T> ProducerFactory<String, T> createProducerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, OrderCreatedEvent> orderCreatedKafkaTemplate() {
		return new KafkaTemplate<>(createProducerFactory());
	}

	@Bean
	public KafkaTemplate<String, OrderCancelledEvent> orderCancelledKafkaTemplate() {
		return new KafkaTemplate<>(createProducerFactory());
	}

	@Bean
	public KafkaTemplate<String, ProductChangedEvent> productChangedKafkaTemplate() {
		return new KafkaTemplate<>(createProducerFactory());
	}
}
