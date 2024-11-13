/**
 * 
 */
package com.lgs.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lgs.api.entity.ProgramEntity;
import com.lgs.api.entity.UserEntity;
import com.lgs.api.repository.ProgramRepository;
import com.lgs.api.repository.UserRepository;
import com.lgs.api.request.ProgramRequest;
import com.lgs.api.request.UserRequest;
import com.lgs.api.response.ProgramResponse;
import com.lgs.api.service.ProgramService;

/**
 * @author Shahzeb Khan
 *
 */
@Service
public class ProgramServiceImpl implements ProgramService {

	@Autowired
	private ProgramRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void addProgram(ProgramRequest request) {
		try {
			ProgramEntity program = new ProgramEntity();
			program.setProgramName(request.getProgramName());
			program.setProgramDescription(request.getProgramDescription());
			program.setProgramFee(request.getProgramFee());
			program.setIsActive(true);
			program.setProgramDuration(request.getProgramDuration());

			repository.save(program);
		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}
	}

	@Override
	public void addLearner(Long programId, ProgramRequest learners) {
		try {
			ProgramEntity program = repository.findById(programId)
					.orElseThrow(() -> new RuntimeException("Id not found " + programId));
			program.setProgramId(programId);
			program.setLearners(mapLearners(learners.getLearners()));
			program.setParticipantCount(program.getParticipantCount() + 1);
			repository.save(program);
		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}
	}

	@Override
	public List<ProgramResponse> getAllPrograms(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		try {
			Sort sort = null;
			if (sortDir.equalsIgnoreCase("asc")) {
				sort = sort.by(sortBy).ascending();
			} else {
				sort = sort.by(sortBy).descending();
			}

			Pageable pageWithSort = PageRequest.of(pageNumber, pageSize, sort);

//			Pageable pageable = PageRequest.of(pageNumber, pageSize);

			Page<ProgramEntity> program = repository.findAll(pageWithSort);

			return program.stream().map(ProgramResponse::new).collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}
	}

	@Override
	public ProgramResponse getProgramById(Long programId) {
		try {
			ProgramEntity program = repository.findById(programId)
					.orElseThrow(() -> new RuntimeException("No id found " + programId));
			return new ProgramResponse(program);
		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}
	}

	@Override
	public void activateProgram(Long programId) {
		try {
			repository.activateisActiveByProductId(programId);
		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}

	}

	@Override
	public void deactivateProgram(Long programId) {
		try {
			repository.deactivateisActiveByProductId(programId);
		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}

	}

	private List<UserEntity> mapLearners(List<UserRequest> source) {
		List<UserEntity> learners = new ArrayList<>();
		for (UserRequest learner : source) {
			UserEntity user = userRepository.findById(learner.getUserId())
					.orElseThrow(() -> new RuntimeException("Id not found " + learner.getUserId()));
			user.setId(learner.getUserId());
			learners.add(user);
		}
		return learners;
	}

}
