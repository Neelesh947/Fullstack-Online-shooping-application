package in.neelesh.online.shopping.util;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class OTPStore {

	private record OtpEntry(String otp, Instant expiry) {
	}

	private final Map<String, OtpEntry> store = new ConcurrentHashMap<>();

	public void saveOtp(String customerId, String otp, long ttlSeconds) {
		store.put(customerId, new OtpEntry(otp, Instant.now().plusSeconds(ttlSeconds)));
	}

	public boolean isValidOtp(String customerId, String otp) {
		var entry = store.get(customerId);
		if (entry == null)
			return false;

		if (Instant.now().isAfter(entry.expiry())) {
			store.remove(customerId);
			return false;
		}
		boolean ok = entry.otp().equals(otp);
		if (ok)
			store.remove(customerId);
		return ok;
	}
}
