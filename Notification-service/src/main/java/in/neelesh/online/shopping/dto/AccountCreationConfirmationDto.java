package in.neelesh.online.shopping.dto;

import java.util.List;

import lombok.Data;

@Data
public class AccountCreationConfirmationDto {

	private String userId;
	private String userName;
	private String firstName;
	private String realm;
	private String email;
	private List<Integer> loginTime;
	private String message;
	private String activationLink;
}
