package com.lgs.api.request;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProgramRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long programId;
	
	private String programName;
	
	private String programDescription;
	
	private String programDuration;
	
	private Double programFee;
	
//	private String participantCount;
	
	private UserRequest trainer;
	
	private List<UserRequest> learners;
	
}
