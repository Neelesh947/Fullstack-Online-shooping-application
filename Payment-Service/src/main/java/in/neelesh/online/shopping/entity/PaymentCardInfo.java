package in.neelesh.online.shopping.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class PaymentCardInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String customerId;
	private String maskedCardNumber;
	private String expiryMonth;
	private String expiryYear;
	private String cardType;
}
