package in.neelesh.online.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.neelesh.online.shopping.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>{

	List<Order> findByCustomerId(String customerId);
}
