package com.lgs.api.service;

import java.util.List;

import com.lgs.api.request.AssignProgramRequest;
import com.lgs.api.request.EnablersRequest;
import com.lgs.api.request.LearnersRequest;
import com.lgs.api.request.UserDTO;
import com.lgs.api.request.UserRequest;
import com.lgs.api.response.EnablerResponse;
import com.lgs.api.response.LearnersResponse;

public interface UserService {

	public void activateUser(Long id);

	public void deactivateUser(Long id);

	public void updateLearner(Long learnerId, LearnersRequest learner);

	public void updateEnabler(Long enablerId, EnablersRequest enabler);

	 public List<LearnersResponse> getAllLearners(
		        Integer pageNumber,
		        Integer pageSize,
		        String sortBy,
		        String sortDir,
		        String searchBy,
		        String searchTerm
		);

	public List<EnablerResponse> getAllEnablers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	public void addPrograms(Long userId, AssignProgramRequest request);

	public LearnersResponse getLearnerById(Long learnerId);

//	public void mapSponsoredLearners(Long enablerId, SponsorRequest sponsorRequest);	

//	public void sponserLearner(Long enablerId, List<UserDTO> learners);

	public void sponsorLearner(Long enablerId, List<UserDTO> learners);

	public void addLearnerInfo(Long learnersId, LearnersRequest request);

	public void addEnablerInfo(Long id, EnablersRequest request);

	public EnablerResponse getEnablerById(Long learnerId);

	public List<EnablerResponse> getAllEnablers();
	
	public void addLearnerInfoR(Long learnerId, LearnersRequest request);
	
	public void verifyLearner(Long learnersId);
}
