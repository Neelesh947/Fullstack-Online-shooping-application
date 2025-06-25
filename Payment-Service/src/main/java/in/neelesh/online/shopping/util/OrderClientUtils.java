package in.neelesh.online.shopping.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import in.neelesh.online.shopping.dto.OrderStatusUpdateDto;
import in.neelesh.online.shopping.enums.OrderStatus;

@Service
public class OrderClientUtils {

	private final RestTemplate restTemplate;

	public OrderClientUtils(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Async
	public void updateOrderStatus(String realm, String orderId, String customerId, OrderStatus status) {
		String url = "http://localhost:1236/" + realm + "/orders/" + orderId + "/status";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth("your-access-token-here");

		HttpEntity<OrderStatusUpdateDto> request = new HttpEntity<>(new OrderStatusUpdateDto(status), headers);

		try {
			restTemplate.put(url, request);
		} catch (Exception e) {
			System.err.println("Failed to update order status: " + e.getMessage());
		}
	}

}
