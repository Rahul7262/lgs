package com.lgs.api.response;

import com.lgs.api.entity.ProgramEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProgramResponse {

	private Long programId;

	private String programName;

	private String programDescription;

	private String programDuration;

	private Double programFee;

	private Integer participantCount;

	private Boolean isActive;
	
	public ProgramResponse(ProgramEntity program) {
		this.programId = program.getProgramId();
		this.programName = program.getProgramName();
		this.programDescription = program.getProgramDescription();
		this.programDuration = program.getProgramDuration();
		this.programFee = program.getProgramFee();
		this.participantCount = program.getParticipantCount();
		this.isActive = program.getIsActive();
	}
}
