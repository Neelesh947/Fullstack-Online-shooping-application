package in.neelesh.online.shopping.entity;

import in.neelesh.online.shopping.enums.PaymentMethod;
import in.neelesh.online.shopping.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private String orderId;
	private String customerId;
	private double amount;

	private String transactionId;

	@Enumerated(EnumType.ORDINAL)
	private PaymentMethod paymentMethod;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
}
