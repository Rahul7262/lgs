package com.lgs.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Shahzeb Khan
 *
 */
@Setter
@Getter
public class LoginRequest implements Serializable {

	private static final long serialVersionUID = 1L;

    private String email;

	private String password;
	
	
}

