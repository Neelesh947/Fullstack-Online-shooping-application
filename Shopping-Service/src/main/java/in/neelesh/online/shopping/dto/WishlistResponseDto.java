package in.neelesh.online.shopping.dto;

import java.util.List;

public record WishlistResponseDto(String wishlistId, String customerId, List<ProductResponseDto> products) {

}
