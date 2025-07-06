package in.neelesh.online.shopping.dto;

import java.math.BigDecimal;

public record PaymentRequestDTOs(BigDecimal amount, String currency, String userId, String merchantName) {

}
