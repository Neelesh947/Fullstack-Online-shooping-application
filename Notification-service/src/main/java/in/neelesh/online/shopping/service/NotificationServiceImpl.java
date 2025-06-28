package in.neelesh.online.shopping.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import in.neelesh.online.shopping.dto.OtpNotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
	
	@Override
	@Async
	public void sendOtp(OtpNotificationRequest notificationRequest) {
		
		log.info("Sending OTP '{}' to customer ID '{}'", notificationRequest.otp(), notificationRequest.customerId());

	}

}
