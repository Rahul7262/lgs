package com.lgs.api.response;

import com.lgs.api.entity.IdType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IdTypeResponse {

	private Long idTypeId;
	
	private String idTypeName;
	
	public IdTypeResponse(IdType idType) {
		this.idTypeId = idType.getIdTypeId();
		this.idTypeName = idType.getIdTypeName();
	}
}
