package in.neelesh.online.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentEvent {

	private String orderId;
    private String customerId;
    private String cartId;
    private boolean paymentSuccess;
}
