package in.neelesh.online.shopping.mapper;

import java.util.List;

import in.neelesh.online.shopping.dto.OrderItemResponseDto;
import in.neelesh.online.shopping.dto.OrderResponseDto;
import in.neelesh.online.shopping.entity.Order;
import in.neelesh.online.shopping.entity.OrderItem;

public class OrderMapper {

	public static OrderResponseDto toDto(Order order) {

		List<OrderItemResponseDto> itemDtos = order.getItems().stream().map(OrderMapper::toDto).toList();

		return new OrderResponseDto(order.getId(), order.getCustomerId(), itemDtos, order.getShippingAddress(),
				order.getStatus().name(), order.getCreateDateTime().toLocalDateTime(),
				order.getUpdateDateTime().toLocalDateTime());
	}

	public static OrderItemResponseDto toDto(OrderItem item) {
		return new OrderItemResponseDto(item.getProduct().getId(), item.getId(), item.getQuantity(), item.getPrice());
	}
}
