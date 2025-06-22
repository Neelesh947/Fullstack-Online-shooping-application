package in.neelesh.online.shopping.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KeycloakUtility {

	RestTemplate restTemplate = new RestTemplate();
}
