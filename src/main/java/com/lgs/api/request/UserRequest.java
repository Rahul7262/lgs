package com.lgs.api.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {

	private Long userId;
	
	private List<ProgramRequest> program;

}
