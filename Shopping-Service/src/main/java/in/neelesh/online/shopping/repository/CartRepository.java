package in.neelesh.online.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.neelesh.online.shopping.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

	Optional<Cart> findByCustomerId(String customerId);
}
