package in.neelesh.online.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.neelesh.online.shopping.entity.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, String> {

//	List<Wishlist> findByCustomerId(String customerId);

	Optional<Wishlist> findByCustomerId(String customerId);

	@Query("SELECT w FROM Wishlist w JOIN w.products p WHERE w.customerId = :customerId AND p.id = :productId")
	Optional<Wishlist> findByCustomerIdAndProductId(@Param("customerId") String customerId,
			@Param("productId") String productId);

	@Query("SELECT CASE WHEN COUNT(w) > 0 THEN true ELSE false END FROM Wishlist w JOIN w.products p WHERE w.customerId = :customerId AND p.id = :productId")
	boolean existsByCustomerIdAndProductId(@Param("customerId") String customerId,
			@Param("productId") String productId);

}
