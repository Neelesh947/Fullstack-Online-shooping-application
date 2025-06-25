package in.neelesh.online.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.neelesh.online.shopping.entity.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, String>{

//	List<Wishlist> findByCustomerId(String customerId);
	
	Optional<Wishlist> findByCustomerId(String customerId);
	
	Optional<Wishlist> findByCustomerIdAndProductId(String customerId, String productId);
	
	boolean existsByCustomerIdAndProductId(String customerId, String productId);
}
