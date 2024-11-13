package com.lgs.api.request;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Shahzeb Khan
 *
 * 
 */

@Setter
@Getter
public class SignupRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	private Set<String> roles;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;
	
//	private LearnersRequest learner;

}

