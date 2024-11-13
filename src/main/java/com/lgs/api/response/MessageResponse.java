package com.lgs.api.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Shahzeb Khan
 *
 */
@Setter
@Getter
public class MessageResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public MessageResponse(String message) {
		this.message = message;
	}

}

