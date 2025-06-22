package in.neelesh.online.shopping.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.neelesh.online.shopping.config.SenderInformationConfig;
import in.neelesh.online.shopping.dto.AccountCreationConfirmationDto;
import in.neelesh.online.shopping.utils.Constants;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCreatedConfirmationService {

	private final JavaMailSender javaMailSender;
	private final ObjectMapper objectMapper;
	private final TemplateEngine templateEngine;
	private final SenderInformationConfig senderInformationConfig;

	@KafkaListener(topics = Constants.USER_CRETAED_CONFIRMATION, groupId = Constants.NOTIFICATION_GROUP)
	public void consumeAccountCreatedEvent(String message) {
		log.info("Received account creation event: {}", message);
		
		try {
			AccountCreationConfirmationDto activation = objectMapper.readValue(message, AccountCreationConfirmationDto.class);
			sendAccountCreatedEmail(
					activation.getEmail(),
					activation.getUserName(),
					activation.getFirstName(),
					activation.getActivationLink()
				);
		} catch (Exception e) {
			log.error("Failed to parse account creation event or send email", e);
		}
	}
	
	private void sendAccountCreatedEmail(String to, String userName, String firstName, String activationLink) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setTo(to);
			helper.setFrom(senderInformationConfig.getSenderInformation());
			helper.setSubject("Activate Your Shopping Account");

			Context context = new Context();
			context.setVariable(Constants.USERNAME, userName);
			context.setVariable(Constants.FIRST_NAME, firstName);
			context.setVariable(Constants.ACTIVATION_LINK, activationLink);
			context.setVariable(Constants.EMAIL, to);

			String html = templateEngine.process(Constants.USER_CRETAED_CONFIRMATION, context);
			helper.setText(html, true);

			javaMailSender.send(mimeMessage);
			log.info("Account creation email sent to {}", to);

		} catch (Exception e) {
			log.error("Failed to send account activation email to {}", to, e);
		}
	}
}
