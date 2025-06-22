package in.neelesh.online.shopping.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.neelesh.online.shopping.config.SenderInformationConfig;
import in.neelesh.online.shopping.dto.AccountDeletionDto;
import in.neelesh.online.shopping.utils.Constants;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountDeactivationService {

	private final JavaMailSender javaMailSender;
	private final ObjectMapper objectMapper;
	private final TemplateEngine templateEngine;
	private final SenderInformationConfig senderInformationConfig;

	@KafkaListener(topics = Constants.ACCOUNT_DEACTIVATION, groupId = Constants.NOTIFICATION_GROUP)
	public void consumeAccountDeletionEvent(String message) {
		log.info("Received account deletion event: {}", message);

		try {
			AccountDeletionDto accountDeletionDto = objectMapper.readValue(message, AccountDeletionDto.class);

			String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

			sendAccountDeletionEmail(accountDeletionDto.getEmail(), accountDeletionDto.getUsername(), currentTime);
		} catch (Exception e) {
			log.error("Failed to parse login event or send email", e);
		}
	}

	private void sendAccountDeletionEmail(String to, String userName, String timestamp) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setTo(to);
			helper.setFrom(senderInformationConfig.getSenderInformation());
			helper.setSubject("Your Account Has Been Deactivated");

			Context context = new Context();
			context.setVariable(Constants.USERNAME, userName);
			context.setVariable(Constants.EMAIL, to);
//			context.setVariable(Constants.ACCOUNT_DELETION_TIME, timestamp);

			String html = templateEngine.process(Constants.ACCOUNT_DEACTIVATION, context);
			helper.setText(html, true);

			javaMailSender.send(mimeMessage);
			log.info("Account deletion email sent to {}", to);

		} catch (Exception e) {
			log.error("Failed to send account deletion email to {}", to, e);
		}

	}
}
