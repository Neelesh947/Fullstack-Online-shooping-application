package in.neelesh.online.shopping.entity;

import in.neelesh.online.shopping.enums.PaymentGateway;
import in.neelesh.online.shopping.enums.PaymentMode;
import in.neelesh.online.shopping.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "paymentGatewayConfigEntity" })
@EqualsAndHashCode(callSuper = false, exclude = { "paymentGatewayConfigEntity" })
@Table(name = "payment_records")
@Builder
public class PaymentRecords extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5270003707476638303L;

	@NotNull(message = "Payment Gateway is mandatory")
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_gateway", nullable = false)
	private PaymentGateway paymentGateway;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_gateway_config_id")
	private PaymentGatewayConfigEntity paymentGatewayConfigEntity;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_mode", nullable = false)
	private PaymentMode paymentMode;

	@NotNull(message = "Amount is required")
	@Column(name = "amount_in_paise", nullable = false)
	private Integer amountInPaise;
	
	@Column(name = "external_payment_id")
	private String externalPaymentId;

	@Column(name = "payment_reference_url")
	private String paymentReferenceUrl;

	@NotNull(message = "Payment status is required")
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status", nullable = false)
	private PaymentStatus paymentStatus;

	@Column(name = "transaction_id")
	private String transactionId;

	@Column(name = "upi_request_id")
	private String upiRequestId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "cart_id")
	private String cartId;

}
