package com.lgs.api.response;

import java.util.ArrayList;
import java.util.List;

import com.lgs.api.entity.ProgramEntity;
import com.lgs.api.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SponsoredLearnersResponse {

	private Long learnersId;

	private String learnersName;

	private String learnersEmail;

	private String learnersMobileNo;

	private String learnersAddress;

	private String learnersGender;

	private String learnersImageUrl;

	private String learnersIdType;

	private String learnersIdentity;

	private Boolean isVerified;

	private List<ProgramResponse> program;

	public SponsoredLearnersResponse(UserEntity learners) {
		this.learnersId = learners.getId();

		this.learnersName = learners.getUsername();

		this.learnersEmail = learners.getEmail();

		this.learnersMobileNo = learners.getUserMobileNo();

		this.learnersAddress = learners.getUserAddress();

		this.learnersGender = learners.getUsersGender();

		this.learnersImageUrl = learners.getUserImageUrl();

		this.learnersIdType = learners.getUserIdType();

		this.learnersIdentity = learners.getUserIdentity();

		this.isVerified = learners.getIsVerified();

		this.program = mapProgram(learners.getProgram());
	}

	private List<ProgramResponse> mapProgram(List<ProgramEntity> source) {
		List<ProgramResponse> programsList = new ArrayList<>();
		for (ProgramEntity program : source) {
			ProgramResponse programResponse = new ProgramResponse();
			programResponse.setProgramId(program.getProgramId());
			programResponse.setProgramName(program.getProgramName());
			programsList.add(programResponse);
		}
		return programsList;
	}
}
