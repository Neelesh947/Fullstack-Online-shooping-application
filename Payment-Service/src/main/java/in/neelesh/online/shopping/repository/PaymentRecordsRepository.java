package in.neelesh.online.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.neelesh.online.shopping.entity.PaymentRecords;

@Repository
public interface PaymentRecordsRepository extends JpaRepository<PaymentRecords, String> {

	PaymentRecords findByTransactionId(String transactionId);
	
	PaymentRecords findByPaymentReferenceUrl(String paymentUrl);
}
