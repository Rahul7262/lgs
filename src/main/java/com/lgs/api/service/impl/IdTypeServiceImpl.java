package com.lgs.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgs.api.entity.IdType;
import com.lgs.api.repository.IdTypeRepository;
import com.lgs.api.request.IdTypeRequest;
import com.lgs.api.response.IdTypeResponse;
import com.lgs.api.service.IdTypeIdService;

@Service
public class IdTypeServiceImpl implements IdTypeIdService {
	
	@Autowired
	private IdTypeRepository repository;

	@Override
	public void addIdType(IdTypeRequest request) {
		try {
			IdType id = new IdType();
			id.setIdTypeName(request.getIdTypeName());
			repository.save(id);
		}
		catch(Exception e) {
			throw new RuntimeException("Error occured "+e.getMessage());
		}
		
	}

	@Override
	public IdTypeResponse idTypeById(Long idTypeId) {
		IdType id = repository.findById(idTypeId).orElseThrow(()-> new RuntimeException("Id not found "+idTypeId));
		return new IdTypeResponse(id);
	}

	@Override
	public List<IdTypeResponse> findAll() {
		List<IdType> ids = repository.findAll();
		return ids.stream().map(IdTypeResponse :: new).collect(Collectors.toList());
	}

	@Override
	public void deleteIdType(Long idTypeId) {
		try {
			repository.deleteById(idTypeId);
		}
		catch(Exception e) {
			throw new RuntimeException("Error occured "+e.getMessage());
		}
	}

	@Override
	public void updateIdType(Long idTypeId, IdTypeRequest request) {
		try {
			IdType id = repository.findById(idTypeId).orElseThrow(()-> new RuntimeException("Id not found "+idTypeId));
			id.setIdTypeName(request.getIdTypeName());
			repository.save(id);
		}
		catch(Exception e) {
			throw new RuntimeException("Error occured "+e.getMessage());
		}
		
	}

	
}
