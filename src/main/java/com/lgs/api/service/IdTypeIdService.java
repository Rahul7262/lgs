package com.lgs.api.service;

import java.util.List;

import com.lgs.api.request.IdTypeRequest;
import com.lgs.api.response.IdTypeResponse;

public interface IdTypeIdService {

	public void addIdType(IdTypeRequest request);
	
	public IdTypeResponse idTypeById(Long idTypeId);
	
	public List<IdTypeResponse> findAll();
	
	public void deleteIdType(Long idTypeId);
	
	public void updateIdType(Long idTypeId, IdTypeRequest request);
}
