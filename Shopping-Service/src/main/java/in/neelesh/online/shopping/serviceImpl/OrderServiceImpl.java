package in.neelesh.online.shopping.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import in.neelesh.online.shopping.dto.OrderCreateDto;
import in.neelesh.online.shopping.dto.OrderInvoiceDto;
import in.neelesh.online.shopping.dto.OrderItemResponseDto;
import in.neelesh.online.shopping.dto.OrderResponseDto;
import in.neelesh.online.shopping.dto.OrderTrackingDto;
import in.neelesh.online.shopping.entity.Order;
import in.neelesh.online.shopping.entity.OrderItem;
import in.neelesh.online.shopping.entity.Product;
import in.neelesh.online.shopping.enums.OrderStatus;
import in.neelesh.online.shopping.mapper.OrderMapper;
import in.neelesh.online.shopping.repository.OrderRepository;
import in.neelesh.online.shopping.repository.ProductRepository;
import in.neelesh.online.shopping.service.OrderService;
import in.neelesh.online.shopping.service.WishlistService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final WishlistService wishlistService;

	@Override
	public OrderResponseDto createOrder(@Valid OrderCreateDto createDto, String realm, String customerId) {
		Order order = new Order();
		order.setCustomerId(customerId);
		order.setStatus(OrderStatus.CREATED);
		order.setShippingAddress(createDto.shippingAddress());

		List<OrderItem> items = createDto.items().stream().map(dtoItem -> {
			Product product = productRepository.findById(dtoItem.productId())
					.orElseThrow(() -> new RuntimeException("Product not found: " + dtoItem.productId()));

			if (product.getQuantity() < dtoItem.quantity()) {
				throw new RuntimeException("Insufficient stock for product: " + product.getName());
			}

			product.setQuantity(product.getQuantity() - dtoItem.quantity());
			productRepository.save(product);

			return OrderItem.builder().productId(product.getId()).quantity(dtoItem.quantity()).price(product.getPrice())
					.order(order).build();
		}).collect(Collectors.toList());

		order.setItems(items);
		Order savedOrder = orderRepository.save(order);

		List<String> orderedProductIds = items.stream().map(OrderItem::getProductId).toList();

		wishlistService.removeOrderedProductFromWishlist(customerId, orderedProductIds);

		List<OrderItemResponseDto> itemDtos = savedOrder.getItems().stream()
				.map(item -> new OrderItemResponseDto(item.getProductId(),
						productRepository.findById(item.getProductId()).map(Product::getName).orElse(""),
						item.getQuantity(), item.getPrice()))
				.collect(Collectors.toList());

		return new OrderResponseDto(savedOrder.getId(), savedOrder.getCustomerId(), itemDtos,
				createDto.shippingAddress(), savedOrder.getStatus().name(),
				savedOrder.getCreateDateTime().toLocalDateTime(), savedOrder.getUpdateDateTime().toLocalDateTime());
	}

	@Override
	public List<OrderResponseDto> getOrdersByCustomerId(String realm, String customerId) {
		List<Order> orders = orderRepository.findByCustomerId(customerId);
		return orders.stream().map(OrderMapper::toDto).toList();
	}

	@Override
	public OrderResponseDto getOrderById(String realm, String customerId, String orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
		if (!order.getCustomerId().equals(customerId)) {
			throw new RuntimeException("Access denied: Order does not belong to customer");
		}

		return OrderMapper.toDto(order);
	}

	@Override
	public void cancelOrder(String realm, String customerId, String orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
		if (!order.getCustomerId().equals(customerId)) {
			throw new RuntimeException("Access denied: Order does not belong to the customer");
		}

		if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED
				|| order.getStatus() == OrderStatus.CANCELLED) {
			throw new RuntimeException("Order cannot be cancelled as it is already " + order.getStatus());
		}
		order.setStatus(OrderStatus.CANCELLED);
		orderRepository.save(order);
	}

	@Override
	public OrderResponseDto updateOrderStatus(String realm, String customerId, String orderId,
			@NotNull(message = "Status must be provided") OrderStatus status) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

		if (!order.getCustomerId().equals(customerId)) {
			throw new RuntimeException("Access denied: Order does not belong to the customer");
		}

		if (!isValidStateTransition(order.getStatus(), status)) {
			throw new RuntimeException("Invalid status transition from " + order.getStatus() + " to " + status);
		}
		order.setStatus(status);
		orderRepository.save(order);

		return OrderMapper.toDto(order);
	}

	private boolean isValidStateTransition(OrderStatus currentStatus, OrderStatus newStatus) {

		switch (currentStatus) {
		case CREATED:
			return newStatus == OrderStatus.PLACED || newStatus == OrderStatus.CANCELLED;
		case PLACED:
			return newStatus == OrderStatus.SHIPPED || newStatus == OrderStatus.CANCELLED;
		case SHIPPED:
			return newStatus == OrderStatus.DELIVERED;
		case DELIVERED:
		case CANCELLED:
			return false;
		default:
			return false;
		}
	}

	@Override
	public List<OrderResponseDto> getAllOrders(String realm, String adminId) {
		List<Order> orders = orderRepository.findAll();
		return orders.stream().map(OrderMapper::toDto).toList();
	}

	@Override
	public List<OrderResponseDto> getOrdersForStoreManager(String realm, String storeManagerId) {
		List<Order> orders = orderRepository.findOrdersByStoreManagerId(storeManagerId);
		return orders.stream().map(OrderMapper::toDto).toList();
	}

	@Override
	public List<OrderResponseDto> getOrdersByStatusForStoreManager(String realm, String storeManagerId, String status) {
		OrderStatus orderStatus;
		try {
			orderStatus = OrderStatus.valueOf(status.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Invalid order status: " + status);
		}
		List<Order> orders = orderRepository.findOrdersByStoreManagerIdAndStatus(storeManagerId, orderStatus);
		return orders.stream().map(OrderMapper::toDto).toList();
	}

	@Override
	public OrderTrackingDto trackOrder(String realm, String customerId, String orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

		if (!order.getCustomerId().equals(customerId)) {
			throw new RuntimeException("Access denied: Order does not belong to the customer");
		}

		LocalDateTime estimatedDeliveryDate = order.getCreateDateTime().toLocalDateTime().plusDays(7);

		List<OrderTrackingDto.TimelineEvent> timeline = List.of(
				new OrderTrackingDto.TimelineEvent("Order Placed", order.getCreateDateTime().toLocalDateTime()),
				new OrderTrackingDto.TimelineEvent("Order Status: " + order.getStatus().name(),
						order.getUpdateDateTime().toLocalDateTime())
		// Add more events here if you track shipment progress
		);
		return new OrderTrackingDto(order.getId(), order.getStatus().name(), estimatedDeliveryDate, timeline);
	}

	@Override
	public OrderInvoiceDto getOrderInvoice(String realm, String customerId, String orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

		if (!order.getCustomerId().equals(customerId)) {
			throw new RuntimeException("Access denied: Order does not belong to the customer");
		}

		List<OrderInvoiceDto.InvoiceItem> invoiceItems = order.getItems().stream()
				.map(item -> new OrderInvoiceDto.InvoiceItem(item.getProductId(), orderId, item.getQuantity(),
						item.getPrice(), item.getPrice()))
				.toList();

		double totalAmount = invoiceItems.stream().mapToDouble(OrderInvoiceDto.InvoiceItem::total).sum();

		return new OrderInvoiceDto(order.getId(), order.getCreateDateTime().toLocalDateTime().toLocalDate(), customerId,
				invoiceItems, totalAmount, order.getStatus().name());
	}

}
