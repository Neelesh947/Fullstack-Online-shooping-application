package in.neelesh.online.shopping.util;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import in.neelesh.online.shopping.dto.PaymentRequestDTOs;

@Component
public class PaymentPayloadBuilder {

	public static JSONObject buildQrCodePayload(PaymentRequestDTOs dtOs, boolean fixedAmount) {
		long closeBy = (System.currentTimeMillis() / 1000) + (5 * 60);
		return new JSONObject().put(Constants.TYPE, Constants.UPI_QR).put(Constants.NAME, dtOs.merchantName())
				.put(Constants.USAGE, Constants.SINGLE_USE).put(Constants.FIXED_AMOUNT, fixedAmount)
				.put(Constants.DESCRIPTION, "Payment done by: -" + dtOs.userId()).put(Constants.CLOSE_BY, closeBy)
				.put(Constants.PAYMENT_AMOUNT, dtOs.amount().multiply(new BigDecimal("100")));
	}

}
