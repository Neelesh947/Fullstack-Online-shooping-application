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
import in.neelesh.online.shopping.dto.LoginEventDto;
import in.neelesh.online.shopping.utils.Constants;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginConfirmationService {

	private final JavaMailSender javaMailSender;
	private final ObjectMapper objectMapper;
	private final TemplateEngine templateEngine;
	private final SenderInformationConfig senderInformationConfig;

	@KafkaListener(topics = Constants.LOGIN_TOPIC, groupId = Constants.NOTIFICATION_GROUP)
	public void consumeLoginEvents(String message) {
		log.info("Received order event: {}", message);

		try {
			LoginEventDto loginEventDto = objectMapper.readValue(message, LoginEventDto.class);
			String to = loginEventDto.getEmail();
			String userName = loginEventDto.getUserName();
			String realm = loginEventDto.getRealm();
			String messageReceved = loginEventDto.getMessage();
			LocalDateTime loginDateTime = LocalDateTime.of(loginEventDto.getLoginTime().get(0),
					loginEventDto.getLoginTime().get(1), loginEventDto.getLoginTime().get(2),
					loginEventDto.getLoginTime().get(3), loginEventDto.getLoginTime().get(4),
					loginEventDto.getLoginTime().get(5));
			String loginTime = loginDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			sendLoginConfirmationEmail(to, userName, realm, loginTime, messageReceved);

		} catch (Exception e) {
			log.error("Failed to parse login event or send email", e);
		}
	}

	private void sendLoginConfirmationEmail(String to, String userName, String realm, String loginTime,
			String messageReceved) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setTo(to);
			helper.setFrom(senderInformationConfig.getSenderInformation());
			helper.setSubject(messageReceved);

			Context context = new Context();
			context.setVariable(Constants.USERNAME, userName);
			context.setVariable(Constants.LOGIN_TIME, loginTime);
			context.setVariable(Constants.REALM, realm);
			context.setVariable(Constants.EMAIL, to);
			context.setVariable(Constants.MESSAGE, messageReceved);

			String html = templateEngine.process(Constants.LOGIN_CONFIRMATION, context);
			helper.setText(html, true);

			javaMailSender.send(mimeMessage);
			log.info("Login confirmation email sent to {}", to);

		} catch (Exception e) {
			log.error("Failed to send login confirmation email to {}", to, e);
		}

	}
}
