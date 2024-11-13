package com.lgs.api.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LearnersRequest {

	private Integer learnersId;
	
	private String learnersName;

//	private String learnersEmail;

	private String learnersMobileNo;

	private String learnersAddress;

	private String learnersGender;

	private String learnersImageUrl;

	private String learnersIdType;

	private String learnersIdentity;
	
	private String contactPersonName;
	
	private String contactPersonMobileNo;
	
	private String contactPersonEmail;
	
	private Boolean inProject;
	
}
