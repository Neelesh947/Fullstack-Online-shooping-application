package in.neelesh.online.shopping.dto;

import java.util.List;

import lombok.Data;

@Data
public class LoginEventDto {

	private String userName;
	private String realm;
	private String email;
	private List<Integer> loginTime;
	private String message;
}
