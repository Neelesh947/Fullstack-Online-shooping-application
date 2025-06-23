package in.neelesh.online.shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.neelesh.online.shopping.dto.WishlistResponseDto;
import in.neelesh.online.shopping.service.WishlistService;
import in.neelesh.online.shopping.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/{realm}/wishlist")
@RequiredArgsConstructor
public class WishlistController {

	private final WishlistService wishlistService;

	@PostMapping("/{productId}")
	public ResponseEntity<Void> addToWishlist(@PathVariable String realm, @PathVariable String productId) {
		String customerId = SecurityUtils.getCurrentUserIdSupplier.get();
		wishlistService.addProductToWishlist(realm, customerId, productId);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<WishlistResponseDto> getWishlist(@PathVariable String realm) {
		String customerId = SecurityUtils.getCurrentUserIdSupplier.get();
		WishlistResponseDto wishlist = wishlistService.getWishlistByCustomerId(realm, customerId);
		return ResponseEntity.ok(wishlist);
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> removeFromWishlist(@PathVariable String realm, @PathVariable String productId) {
		String customerId = SecurityUtils.getCurrentUserIdSupplier.get();
		wishlistService.removeProductFromWishlist(realm, customerId, productId);
		return ResponseEntity.noContent().build();
	}
}
