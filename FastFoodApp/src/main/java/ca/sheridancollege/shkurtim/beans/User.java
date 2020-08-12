package ca.sheridancollege.shkurtim.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Long userId;
	@NonNull
	private String  email, encryptedPassword;
	
	@NonNull
	private boolean enabled;
}
