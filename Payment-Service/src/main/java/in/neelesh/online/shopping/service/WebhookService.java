package in.neelesh.online.shopping.service;

public interface WebhookService {

	void processWebhook(String payload, String razorpaySignature);

}
