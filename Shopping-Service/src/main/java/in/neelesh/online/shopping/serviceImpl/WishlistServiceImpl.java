package in.neelesh.online.shopping.serviceImpl;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import in.neelesh.online.shopping.dto.ProductResponseDto;
import in.neelesh.online.shopping.dto.WishlistResponseDto;
import in.neelesh.online.shopping.entity.Product;
import in.neelesh.online.shopping.entity.ProductImage;
import in.neelesh.online.shopping.entity.Wishlist;
import in.neelesh.online.shopping.repository.ProductRepository;
import in.neelesh.online.shopping.repository.WishlistRepository;
import in.neelesh.online.shopping.service.WishlistService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

	private final WishlistRepository wishlistRepository;
	private final ProductRepository productRepository;

	@Override
	public void addProductToWishlist(String realm, String customerId, String productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
				.orElseGet(() -> Wishlist.builder().customerId(customerId).build());

		boolean alreadyExists = wishlist.getProducts().stream().anyMatch(p -> p.getId().equals(productId));
		if (alreadyExists) {
			throw new RuntimeException("Product already in wishlist");
		}
		wishlist.getProducts().add(product);
		wishlistRepository.save(wishlist);
	}

	@Override
	public void removeProductFromWishlist(String realm, String customerId, String productId) {
		Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new RuntimeException("Wishlist not found"));

		boolean removed = wishlist.getProducts().removeIf(p -> p.getId().equals(productId));

		if (!removed) {
			throw new RuntimeException("Product not found in wishlist");
		}

		wishlistRepository.save(wishlist);
	}

	@Override
	public WishlistResponseDto getWishlistByCustomerId(String realm, String customerId) {
		Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new RuntimeException("Wishlist not found"));

		List<ProductResponseDto> products = wishlist.getProducts().stream()
				.map((Product product) -> new ProductResponseDto(product.getId(), product.getName(),
						product.getDescription(), product.getPrice(),
						product.getImages().stream()
								.map((ProductImage img) -> Base64.getEncoder().encodeToString(img.getImageData()))
								.collect(Collectors.toList()),
						product.getQuantity()))
				.collect(Collectors.toList());

		return new WishlistResponseDto(wishlist.getId(), wishlist.getCustomerId(), products);
	}
	
	@Async
	@Transactional
	public void removeOrderedProductFromWishlist(String customerId, List<String> orderedProductId) {
		wishlistRepository.findByCustomerId(customerId).ifPresent(wishlist -> {
			List<Product> productList = wishlist.getProducts().stream()
					.filter(product -> !orderedProductId.contains(product.getId())).toList();
			wishlist.setProducts(productList);
			wishlistRepository.save(wishlist);
		});
	}
}
