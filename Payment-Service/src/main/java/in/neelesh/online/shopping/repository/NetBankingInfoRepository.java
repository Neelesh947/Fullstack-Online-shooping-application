package in.neelesh.online.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.neelesh.online.shopping.entity.PaymentNetBankingInfo;

@Repository
public interface NetBankingInfoRepository extends JpaRepository<PaymentNetBankingInfo, String> {

}
