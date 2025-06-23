package in.neelesh.online.shopping.dto;

import in.neelesh.online.shopping.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateDto(@NotNull(message = "Status must be provided") OrderStatus status) {

}
