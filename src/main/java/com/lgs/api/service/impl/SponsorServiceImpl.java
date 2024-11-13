package com.lgs.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgs.api.entity.RoleEntity;
import com.lgs.api.entity.UserEntity;
import com.lgs.api.repository.UserRepository;
import com.lgs.api.request.UserDTO;
import com.lgs.api.response.SponsoredLearnersResponse;
import com.lgs.api.service.SponsorService;
import com.lgs.api.util.ERole;

@Service
public class SponsorServiceImpl implements SponsorService {

	@Autowired
	private UserRepository repository;

	@Override
	public void sponsorLearner(Long enablerId, List<UserDTO> learners) {
		UserEntity enabler = repository.findById(enablerId).orElse(null);
		if (enabler != null) {
			if(enablerHasEnablerRole(enabler)) {
			List<UserEntity> existingLearners = enabler.getSponseredLearners();
			List<UserEntity> newLearners = mapSponsered(learners);
			for (UserEntity newLearner : newLearners) {
				if (!existingLearners.contains(newLearner)) {
					existingLearners.add(newLearner);
				}
			}
			enabler.setSponseredLearners(existingLearners);
			repository.save(enabler);
			}
		} else {
			throw new RuntimeException("Enabler with ID " + enablerId + " not found");
		}
	}
	
	@Override
	public List<SponsoredLearnersResponse> findAllLearnersBySponsorId(Long enablerId) {
		
		List<UserEntity> findAllLearnersByEnablerId = repository.findAllLearnersByEnablerId(enablerId);
		
		List<SponsoredLearnersResponse> findAllByEnablerId = findAllLearnersByEnablerId.stream().map(SponsoredLearnersResponse :: new).collect(Collectors.toList());
		
		return findAllByEnablerId;
	}

	private List<UserEntity> mapSponsered(List<UserDTO> learners) {
		List<UserEntity> learnersList = new ArrayList<>();
		for (UserDTO user : learners) {
			UserEntity learner = repository.findById(user.getId()).orElse(null);
			if (learner != null) {
				learnersList.add(learner);
			} else {
				throw new RuntimeException("Learner with ID " + user.getId() + " not found");
			}
		}
		return learnersList;
	}
	
	private boolean enablerHasEnablerRole(UserEntity user) {
	    Set<RoleEntity> roles = user.getRoles();
	    for (RoleEntity role : roles) {
	        if (role.getName() == ERole.ROLE_ENABLER) {
	            return true;
	        }
	    }
	    return false;
	}

}
