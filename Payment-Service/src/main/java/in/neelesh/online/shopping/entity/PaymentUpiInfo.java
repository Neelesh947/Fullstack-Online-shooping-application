package in.neelesh.online.shopping.entity;

import jakarta.persistence.Entity;
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
public class PaymentUpiInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String customerId;
	private String upiId;
}
