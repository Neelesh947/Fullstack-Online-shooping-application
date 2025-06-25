package in.neelesh.online.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.neelesh.online.shopping.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

	Optional<Payment> findByOrderId(String orderId);
}
