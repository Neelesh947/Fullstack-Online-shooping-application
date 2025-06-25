package in.neelesh.online.shopping.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.neelesh.online.shopping.dto.OrderCreateDto;
import in.neelesh.online.shopping.dto.OrderInvoiceDto;
import in.neelesh.online.shopping.dto.OrderResponseDto;
import in.neelesh.online.shopping.dto.OrderStatusUpdateDto;
import in.neelesh.online.shopping.dto.OrderTrackingDto;
import in.neelesh.online.shopping.service.OrderService;
import in.neelesh.online.shopping.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{realm}/orders")
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderCreateDto createDto,
			@PathVariable String realm) {
		String customerId = SecurityUtils.getCurrentUserIdSupplier.get();
		OrderResponseDto createOrder = orderService.createOrder(createDto, realm, customerId);
		return ResponseEntity.status(HttpStatus.CREATED).body(createOrder);
	}

	@GetMapping
	public ResponseEntity<List<OrderResponseDto>> getOrders(@PathVariable String realm) {
		String customerId = SecurityUtils.getCurrentUserIdSupplier.get();
		List<OrderResponseDto> orders = orderService.getOrdersByCustomerId(realm, customerId);
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable String realm, @PathVariable String orderId) {
		String customerId = SecurityUtils.getCurrentUserIdSupplier.get();
		OrderResponseDto order = orderService.getOrderById(realm, customerId, orderId);
		return ResponseEntity.ok(order);
	}

	@PutMapping("/{orderId}/status")
	public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable String realm, @PathVariable String orderId,
			@Valid @RequestBody OrderStatusUpdateDto statusUpdateDto) {
		String customerId = SecurityUtils.getCurrentUserIdSupplier.get();
		OrderResponseDto updatedOrder = orderService.updateOrderStatus(realm, customerId, orderId,
				statusUpdateDto.status());
		return ResponseEntity.ok(updatedOrder);
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<Void> cancelOrder(@PathVariable String realm, @PathVariable String orderId) {
		String customerId = SecurityUtils.getCurrentUserIdSupplier.get();
		orderService.cancelOrder(realm, customerId, orderId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/super-admin/all")
	public ResponseEntity<List<OrderResponseDto>> getAllOrdersForAdmin(@PathVariable String realm) {
		String adminId = SecurityUtils.getCurrentUserIdSupplier.get();
		List<OrderResponseDto> orders = orderService.getAllOrders(realm, adminId);
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/store-manager")
	public ResponseEntity<List<OrderResponseDto>> getOrdersByStoreManager(@PathVariable String realm) {
		String storeManagerId = SecurityUtils.getCurrentUserIdSupplier.get();
		List<OrderResponseDto> orders = orderService.getOrdersForStoreManager(realm, storeManagerId);
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/store-manager/status/{status}")
	public ResponseEntity<List<OrderResponseDto>> getOrdersByStatusForStoreManager(@PathVariable String realm,
			@PathVariable String status) {

		String storeManagerId = SecurityUtils.getCurrentUserIdSupplier.get();
		List<OrderResponseDto> orders = orderService.getOrdersByStatusForStoreManager(realm, storeManagerId, status);
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/{orderId}/track")
	public ResponseEntity<OrderTrackingDto> trackOrder(@PathVariable String realm, @PathVariable String orderId) {
	    String customerId = SecurityUtils.getCurrentUserIdSupplier.get();
	    return ResponseEntity.ok(orderService.trackOrder(realm, customerId, orderId));
	}
	
	@GetMapping("/{orderId}/invoice")
	public ResponseEntity<OrderInvoiceDto> getInvoice(@PathVariable String realm, @PathVariable String orderId) {
	    String customerId = SecurityUtils.getCurrentUserIdSupplier.get();
	    return ResponseEntity.ok(orderService.getOrderInvoice(realm, customerId, orderId));
	}
}
