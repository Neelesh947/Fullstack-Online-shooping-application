package in.neelesh.online.shopping.dto;

import java.time.LocalDate;
import java.util.List;

public record OrderInvoiceDto(String orderId, LocalDate orderDate, String customerId, List<InvoiceItem> items,
		double totalAmount, String status) {
	public record InvoiceItem(String productId, String productName, int quantity, double unitPrice, double total) {
	}
}
