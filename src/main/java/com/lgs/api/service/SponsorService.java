package com.lgs.api.service;

import java.util.List;

import com.lgs.api.entity.UserEntity;
import com.lgs.api.request.UserDTO;
import com.lgs.api.response.SponsoredLearnersResponse;

public interface SponsorService {

	public void sponsorLearner(Long enablerId, List<UserDTO> learners);
	
	public List<SponsoredLearnersResponse> findAllLearnersBySponsorId(Long enablerId);
}
