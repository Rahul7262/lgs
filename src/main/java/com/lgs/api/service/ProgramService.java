package com.lgs.api.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.lgs.api.request.ProgramRequest;
import com.lgs.api.response.ProgramResponse;

/**
 * 
 * @author Shahzeb Khan
 *
 */
public interface ProgramService {

	public void addProgram(ProgramRequest request);
	
	public void addLearner(Long programId, ProgramRequest learners);
	
	public List<ProgramResponse> getAllPrograms(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	public ProgramResponse getProgramById(Long programId);
	
	public void activateProgram(Long programId);
	
	public void deactivateProgram(Long programId);
	
	
}
