package com.lgs.api.response;

import com.lgs.api.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnablerResponse {
	
	private Long enablersId;

	private String enablersName;
	
	private String enablersGST;
	
	private String enablerMobileNo;
	
	private String enablerGender;
	
	private String enablerIdType;
	
	private String enablerIdentity;
	
	private String enablerQualification;
	
	private Boolean isActive;
	
	public EnablerResponse(UserEntity user) {
		this.enablersId = user.getId();
		this.enablersName = user.getUsername();
		this.enablersGST = user.getUserGST();
		this.enablerMobileNo = user.getUserMobileNo();
		this.enablerGender = user.getUsersGender();
		this.enablerIdType = user.getUserIdType();
		this.enablerIdentity = user.getUserIdentity();
		this.enablerQualification = user.getQualification();
		this.isActive = user.getIsActive();
	}
}
