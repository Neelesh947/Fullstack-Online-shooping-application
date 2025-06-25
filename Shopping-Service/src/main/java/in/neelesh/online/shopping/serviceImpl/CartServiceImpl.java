package in.neelesh.online.shopping.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import in.neelesh.online.shopping.dto.CartItemRequestDto;
import in.neelesh.online.shopping.dto.CartItemResponseDto;
import in.neelesh.online.shopping.dto.CartResponseDto;
import in.neelesh.online.shopping.entity.Cart;
import in.neelesh.online.shopping.entity.CartItem;
import in.neelesh.online.shopping.repository.CartRepository;
import in.neelesh.online.shopping.repository.ProductRepository;
import in.neelesh.online.shopping.service.CartService;
import in.neelesh.online.shopping.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final WishlistService wishlistService;

	@Override
	public CartResponseDto getCart(String customerId, String realm) {
		Cart cart = cartRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new RuntimeException("Cart not found"));

		List<CartItemResponseDto> itemsDto = cart.getItems().stream().map(item -> {
			var product = productRepository.findById(item.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found for id: " + item.getProductId()));
			double price = product.getPrice();
			int quantity = item.getQuantity();
			return new CartItemResponseDto(product.getId(), product.getName(), price, quantity, price * quantity);
		}).collect(Collectors.toList());

		double totalCartPrice = itemsDto.stream().mapToDouble(CartItemResponseDto::totalPrice).sum();
		return new CartResponseDto(cart.getId(), cart.getCustomerId(), itemsDto, totalCartPrice);
	}

	@Override
	public CartResponseDto addItemToCart(String customerId, String realm, @Valid CartItemRequestDto dto) {
		Cart cart = cartRepository.findByCustomerId(customerId)
				.orElseGet(() -> Cart.builder().customerId(customerId).build());

		CartItem existingItem = cart.getItems().stream().filter(item -> item.getProductId().equals(dto.productId()))
				.findFirst().orElse(null);

		if (existingItem != null) {
			existingItem.setQuantity(existingItem.getQuantity() + dto.quantity());
		} else {
			CartItem newItem = CartItem.builder().productId(dto.productId()).quantity(dto.quantity()).cart(cart)
					.build();
			cart.getItems().add(newItem);
		}
		cartRepository.save(cart);
		return getCart(customerId, realm);
	}

	@Override
	public CartResponseDto updateCartItem(String customerId, String realm, @Valid CartItemRequestDto dto) {
		Cart cart = cartRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new RuntimeException("Cart not found for customer: " + customerId));

		CartItem existingItem = cart.getItems().stream().filter(item -> item.getProductId().equals(dto.productId()))
				.findFirst().orElseThrow(() -> new RuntimeException("Product not found in cart: " + dto.productId()));

		existingItem.setQuantity(dto.quantity());
		cartRepository.save(cart);
		return getCart(customerId, realm);
	}

	@Override
	public CartResponseDto removeItemFromCart(String customerId, String realm, String productId) {
		Cart cart = cartRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new RuntimeException("Cart not found for customer: " + customerId));

		boolean removed = cart.getItems().removeIf(item -> item.getProductId().equals(productId));

		if (!removed) {
			throw new RuntimeException("Product not found in cart: " + productId);
		}

		cartRepository.save(cart);
		return getCart(customerId, realm);
	}

	@Override
	public void clearCart(String customerId, String realm) {
		Cart cart = cartRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new RuntimeException("Cart not found for customer: " + customerId));

		cart.getItems().clear();
		cartRepository.save(cart);
	}

	@Override
	public void moveItemToWishlist(String customerId, String realm, String productId, int quantity) {
		Cart cart = cartRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new RuntimeException("Cart not found for customer: " + customerId));
		boolean removed = cart.getItems().removeIf(item -> item.getProductId().equals(productId));
		if (!removed) {
			throw new RuntimeException("Product not found in cart");
		}
		cartRepository.save(cart);
		wishlistService.addProductToWishlist(realm, customerId, productId);
	}

}
