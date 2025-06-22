package in.neelesh.online.shopping.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SenderInformationConfig {

	@Value("${mail.sent.from}")
	private String senderInformation;
}
