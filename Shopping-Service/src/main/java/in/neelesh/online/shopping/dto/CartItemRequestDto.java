package in.neelesh.online.shopping.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CartItemRequestDto(@NotBlank String productId, @Min(1) int quantity) {
}
