package in.neelesh.online.shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.neelesh.online.shopping.dto.OtpNotificationRequest;
import in.neelesh.online.shopping.service.NotificationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/{realm}/notification/")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	@PostMapping("/send-otp")
	public ResponseEntity<String> sendOtp(@RequestBody OtpNotificationRequest notificationRequest,
			@PathVariable String realm) {
		notificationService.sendOtp(notificationRequest);
		return ResponseEntity.ok("OTP notification sent successfully");
	}
}
