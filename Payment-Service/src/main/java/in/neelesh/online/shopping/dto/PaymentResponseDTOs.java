package in.neelesh.online.shopping.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentResponseDTOs(String paymentrefernceUrl, String paymentQrCode, String upiRequestId) {

}
