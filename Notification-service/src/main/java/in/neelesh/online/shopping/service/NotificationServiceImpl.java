package in.neelesh.online.shopping.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import in.neelesh.online.shopping.config.SenderInformationConfig;
import in.neelesh.online.shopping.dto.OtpNotificationRequest;
import in.neelesh.online.shopping.utils.Constants;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

	private final JavaMailSender javaMailSender;
	private final TemplateEngine templateEngine;
	private final SenderInformationConfig senderInformationConfig;

	@Override
	@Async
	public void sendOtp(OtpNotificationRequest notificationRequest) {

		log.info("Sending OTP '{}' to customer ID '{}'", notificationRequest.otp(), notificationRequest.customerId());

		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setTo(notificationRequest.email());
			helper.setFrom(senderInformationConfig.getSenderInformation());
			helper.setSubject("Your One-Time Password (OTP) for Verification");

			Context context = new Context();
			context.setVariable("customerName", notificationRequest.customerName());
			context.setVariable("otp", notificationRequest.otp());

			String htmlBody = templateEngine.process(Constants.SEND_OTP, context);
			helper.setText(htmlBody, true);

			javaMailSender.send(mimeMessage);
			log.info("OTP email sent successfully to {}", notificationRequest.email());

		} catch (Exception e) {
			log.error("Failed to send OTP to {}: {}", notificationRequest.email(), e.getMessage(), e);
			throw new RuntimeException("OTP sending failed", e);
		}
	}

}
