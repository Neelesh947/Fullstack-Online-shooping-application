package in.neelesh.online.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.neelesh.online.shopping.entity.Order;
import in.neelesh.online.shopping.enums.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

	List<Order> findByCustomerId(String customerId);

	@Query("SELECT DISTINCT o FROM Order o JOIN o.items oi JOIN oi.product p WHERE p.userId = :storeManagerId")
	List<Order> findOrdersByStoreManagerId(@Param("storeManagerId") String storeManagerId);

	@Query("SELECT DISTINCT o FROM Order o JOIN o.items oi JOIN oi.product p WHERE p.userId = :storeManagerId AND o.status = :status")
	List<Order> findOrdersByStoreManagerIdAndStatus(@Param("storeManagerId") String storeManagerId,
			@Param("status") OrderStatus status);
}
