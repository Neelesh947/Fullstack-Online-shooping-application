package in.neelesh.online.shopping.service;

import in.neelesh.online.shopping.dto.OtpNotificationRequest;

public interface NotificationService {

	void sendOtp(OtpNotificationRequest notificationRequest);

}
