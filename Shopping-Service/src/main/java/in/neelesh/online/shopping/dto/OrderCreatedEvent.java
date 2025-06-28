package in.neelesh.online.shopping.dto;

import java.util.List;

public record OrderCreatedEvent(String orderId, String customerId, String realm, List<String> productIds,
		double totalAmount) {

}
