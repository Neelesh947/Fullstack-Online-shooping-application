package in.neelesh.online.shopping.service;

import in.neelesh.online.shopping.dto.WishlistResponseDto;

public interface WishlistService {

	void addProductToWishlist(String realm, String customerId, String productId);

	void removeProductFromWishlist(String realm, String customerId, String productId);

	WishlistResponseDto getWishlistByCustomerId(String realm, String customerId);

}
