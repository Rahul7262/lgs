package com.lgs.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lgs.api.entity.ProgramEntity;
import com.lgs.api.entity.RoleEntity;
import com.lgs.api.entity.UserEntity;
import com.lgs.api.repository.ProgramRepository;
import com.lgs.api.repository.UserRepository;
import com.lgs.api.request.AssignProgramRequest;
import com.lgs.api.request.EnablersRequest;
import com.lgs.api.request.LearnersRequest;
import com.lgs.api.request.ProgramRequest;
import com.lgs.api.request.SponsorRequest;
import com.lgs.api.request.UserDTO;
import com.lgs.api.request.UserRequest;
import com.lgs.api.response.EnablerResponse;
import com.lgs.api.response.LearnersResponse;
import com.lgs.api.service.UserService;
import com.lgs.api.util.ERole;
import com.lgs.api.util.SendMail;

@Service
public class UserServiceRestImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private ProgramRepository programRepository;

	@Autowired
	private SendMail mailSender;

	@Override
	public void activateUser(Long id) {
		try {
			repository.activateisActiveById(id);
		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}

	}

	@Override
	public void deactivateUser(Long id) {
		try {
			repository.deactivateisActiveById(id);
		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}

	}

	@Override
	public void updateLearner(Long learnerId, LearnersRequest learner) {
		try {
			UserEntity user = new UserEntity();
			user.setId(learnerId);
			user.setUsername(learner.getLearnersName());
			user.setUserMobileNo(learner.getLearnersMobileNo());
			user.setUserAddress(learner.getLearnersAddress());
			user.setUsersGender(learner.getLearnersGender());
			user.setUserImageUrl(learner.getLearnersImageUrl());
			user.setUserIdType(learner.getLearnersIdType());
			user.setUserIdentity(learner.getLearnersIdentity());
			repository.save(user);

		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}

	}

	@Override
	public void updateEnabler(Long enablerId, EnablersRequest enabler) {
		try {
//			UserEntity user = repository.findByEmail(email).orElseThrow(() -> new RuntimeException());
//			user.setEmail(email);
//			user.setUsername(enabler.getEnablersName());
//			user.setUserIdentity(enabler.getEnablersGST());
////			user.setContactPersonName(enabler.getContactPersonName());
////			user.setContactPersonEmail(enabler.getContactPersonEmail());
////			user.setContactPersonMobileNo(enabler.getContactPersonMobileNo());
//			repository.save(user);
		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}

	}

//	@Override
//	public List<LearnersResponse> getAllLearners(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
//		List<UserEntity> learners = repository.findAll();
//		
//		try {
//			Sort sort = null;
//			if(sortDir.equalsIgnoreCase("asc")) {
//				sort = sort.by(sortBy).ascending();
//			}
//			else {
//				sort = sort.by(sortBy).descending();
//			}
//			
//			Pageable pageWithSort = PageRequest.of(pageNumber, pageSize, sort);
//			
////			Pageable pageable = PageRequest.of(pageNumber, pageSize);
//	
//			Page<UserEntity> learners = repository.findAll(pageWithSort); 
//			
//			return learners.stream()
//					.filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_LEARNER)))
//					.map(LearnersResponse::new).collect(Collectors.toList());
//		} catch (Exception e) {
//			throw new RuntimeException("Error occured " + e.getMessage());
//		}

//		public List<LearnersResponse> getAllLearners(
//		        Integer pageNumber,
//		        Integer pageSize,
//		        String sortBy,
//		        String sortDir,
//		        String searchBy,
//		        String searchTerm
//		) {
//		    try {
//		        Pageable pageWithSort = PageRequest.of(pageNumber, pageSize, sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
//		        Page<UserEntity> learnerPage;
//		        
//		        if ("learnersName".equals(searchBy)) {
//		            // You need to implement the search by invoice number logic here
//		            // For example, you might have a method in your repository like findByInvoiceNumber()
//		            learnerPage = repository.findByUsernameContaining(searchTerm, pageWithSort);
//		        } else {
//		            // Default behavior without search
//		            learnerPage = repository.findAll(pageWithSort);
//		        }
//		        
//		        List<UserEntity> learners = learnerPage.getContent();
//		        
//		        return learners.stream()
//		                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_LEARNER)))
//		                .map(LearnersResponse::new)
//		                .collect(Collectors.toList());
//		    } catch (Exception e) {
//		        throw new RuntimeException("Error occurred: " + e.getMessage());
//		    }
//		}
//	}    public List<LearnersResponse> getAllLearners(
	@Override
	public List<LearnersResponse> getAllLearners(Integer pageNumber, Integer pageSize, String sortBy, String sortDir,
			String searchBy, String searchTerm) {
		try {
			Pageable pageWithSort = PageRequest.of(pageNumber, pageSize,
					sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
			Page<UserEntity> learnerPage;

			if ("learnersName".equals(searchBy)) {
				// Search by learnersName
				learnerPage = repository.findByUsernameContaining(searchTerm, pageWithSort);
			} else {
				// Default behavior without search
				learnerPage = repository.findAll(pageWithSort);
			}

			List<UserEntity> learners = learnerPage.getContent();

			return learners.stream().filter(
					user -> user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_LEARNER)))
					.map(LearnersResponse::new).collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException("Error occurred: " + e.getMessage());
		}
	}

	
	public void addPrograms(Long userId, UserRequest request) {
		try {
			UserEntity learner = repository.findById(userId)
					.orElseThrow(() -> new RuntimeException("Id not found " + userId));
			learner.setProgram(mapProgram(request.getProgram()));
			repository.save(learner);

		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}
	}
	@Override
	public void addPrograms(Long userId, AssignProgramRequest request) {
	    try {
	        UserEntity learner = repository.findById(userId)
	                .orElseThrow(() -> new RuntimeException("Id not found " + userId));
	        
	 
	        for (ProgramRequest programRequest : request.getProgram()) {
	            ProgramEntity programEntity = mapPrograms(programRequest);
	      
	            if (!learner.getProgram().contains(programEntity)) {
	                learner.getProgram().add(programEntity);
	            }
	        }
	        
	        repository.save(learner);
	    } catch (Exception e) {
	        throw new RuntimeException("Error occurred " + e.getMessage());
	    }
	}
	
	private ProgramEntity mapPrograms(ProgramRequest source) {
		ProgramEntity program = programRepository.findById(source.getProgramId()).get();
		program.setProgramId(program.getProgramId());
		program.setParticipantCount(program.getParticipantCount() + 1);
		return program;
	}

//	private ProgramEntity mapProgram(AssignProgramRequest source) {
//		List<E>
//	
//	}

//	@Override
	public void mapSponsoredLearners(Long enablerId, SponsorRequest sponsorRequest) {
		UserEntity enabler = repository.findById(enablerId)
				.orElseThrow(() -> new RuntimeException("Enabler not found"));

		repository.save(enabler);
	}

	private List<ProgramEntity> mapProgram(List<ProgramRequest> source) {
		List<ProgramEntity> programs = new ArrayList<>();
		for (ProgramRequest program : source) {
			ProgramEntity programEntity = programRepository.findById(program.getProgramId())
					.orElseThrow(() -> new RuntimeException("Id not found " + program.getProgramId()));
			programEntity.setProgramId(program.getProgramId());
			programEntity.setParticipantCount(programEntity.getParticipantCount() + 1);
			programs.add(programEntity);
		}
		return programs;
	}
	


	@Override
	public void sponsorLearner(Long enablerId, List<UserDTO> learners) {
		try {
			UserEntity enabler = repository.findById(enablerId)
					.orElseThrow(() -> new RuntimeException("Id not found: " + enablerId));
//	        enabler.setId(enablerId);
			enabler.setSponseredLearners(mapLearners(learners));
			repository.save(enabler);
		} catch (Exception e) {
			throw new RuntimeException("Error occurred: " + e.getMessage());
		}
	}

	private List<UserEntity> mapLearners(List<UserDTO> source) {
		List<UserEntity> learnersList = new ArrayList<>();
		for (UserDTO learner : source) {
			UserEntity sponsored = repository.findById(learner.getId())
					.orElseThrow(() -> new RuntimeException("Cannot find id: " + learner.getId()));
			learnersList.add(sponsored);
		}
		return learnersList;
	}

	@Override
	public void addLearnerInfo(Long learnersId, LearnersRequest request) {
		try {
			UserEntity user = repository.findById(learnersId)
					.orElseThrow(() -> new RuntimeException("Id not found " + learnersId));
			if(user != null) {
			user.setUsername(request.getLearnersName());
			user.setUserMobileNo(request.getLearnersMobileNo());
			user.setUserAddress(request.getLearnersAddress());
			user.setUsersGender(request.getLearnersGender());
			user.setUserImageUrl(request.getLearnersImageUrl());
			user.setUserIdType(request.getLearnersIdType());
			user.setUserIdentity(request.getLearnersIdentity());
			user.setContactPersonName(request.getContactPersonName());
			user.setContactPersonMobileNo(request.getContactPersonMobileNo());
			user.setContactPersonEmail(request.getContactPersonEmail());
			user.setIsDetailsFilled(true);
			if(user.getIsVerified() == null || !user.getIsVerified()) {
				user.setIsVerified(false);
			}
			else {
				user.setIsVerified(true);
			}
			String subject = "Verification Request: Confirmation Required for Student Verification";
			String htmlBody = "<html><body style=\"font-family: Arial, sans-serif;\">"
					+ "<p style=\"color:black;\">We are reaching out to you regarding a student verification request made by <b>"
					+ request.getLearnersName() + "</b>. It appears that <b>" + request.getLearnersName()
					+ "</b> has designated you as the responsible party for their verification process.</p>"
					+ "<p style=\"color:black;\">As part of our verification procedure, we kindly request your confirmation of <b>"
					+ request.getLearnersName()
					+ "</b>'s details. Your verification is crucial in ensuring the accuracy and authenticity of the student's information within our records.</p>"
					+ "<p style=\"color:black;\">Please take a moment to review the following details:</p>" + "<ul>"
					+ "<li style=\"color:black;\">Student Name: " + request.getLearnersName() + "</li>"
					+ "<li style=\"color:black;\">Verification Purpose: Enroll in an IT program</li>" + "</ul>"
					+ "<p style=\"color:black;\">By confirming the accuracy of these details, you acknowledge your responsibility for the student's verification process. Please note that any discrepancies or inaccuracies in the provided information may have consequences for both the student and yourself.</p>"
					+ "<p style=\"color:black;\">Your cooperation in this matter is greatly appreciated. Should you have any questions or require further clarification, please do not hesitate to contact us at lavanyagurukul.com.</p>"
					+ "<p style=\"color:black;\">Thank you for your attention to this matter.</p>"
					+ "<p style=\"color:black;\">Best regards,<br/>lavanyagurukul</p>"
					+ "<p><button style=\"background-color: #4CAF50; /* Green */" + "border: none;" + "color: white;"
					+ "padding: 15px 32px;" + "text-align: center;" + "text-decoration: none;"
					+ "display: inline-block;" + "font-size: 18px; /* Increased font size */"
					+ "font-weight: bold; /* Bold font */" + "border-radius: 10px;\">"
					+ "<a href=\"https://6036-2405-201-3044-480b-b427-ebbc-e496-d85e.ngrok-free.app/api/v1/users/verfify/" + user.getId()
//					+ "<a href=\"http://localhost:8081/api/v1/users/verfify/" + user.getId()
					+ "\" style=\"color: black; text-decoration: none;\">"
					// Button text color set to black
					+ "Confirm Verification" + "</a>" + "</button></p>";
			if(!user.getIsVerified()) {
			mailSender.sendEmail(request.getContactPersonEmail(), subject, htmlBody);
			}
			repository.save(user);
			}
			else {
				throw new RuntimeException("Error occured ");
			}
		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}
	}

	@Override
	public void addEnablerInfo(Long id, EnablersRequest request) {
		try {

			UserEntity user = repository.findById(id).orElseThrow(() -> new RuntimeException("Id not found " + id));
			user.setUsername(request.getEnablersName());
			user.setUserGST(request.getEnablersGST());
			user.setQualification(request.getEnablerQualification());
			user.setUserMobileNo(request.getEnablerMobileNo());
			user.setUsersGender(request.getEnablerGender());
			user.setUserIdType(request.getEnablerIdType());
			user.setUserIdentity(request.getEnablerIdentity());
			user.setUserAddress(request.getEnablerAddress());
			user.setIsDetailsFilled(true);
			repository.save(user);

		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}

	}

//	private UserDetailsImpl getCurrentUser(Principal principal) {
//		return userDetailsService.getCurrentUser(principal);
//	}

	private boolean learnerHasLearnerRole(UserEntity user) {
		Set<RoleEntity> roles = user.getRoles();
		for (RoleEntity role : roles) {
			if (role.getName() == ERole.ROLE_LEARNER) {
				return true;
			}
		}
		return false;
	}

	private boolean userHasEnablerRole(UserEntity user) {
		Set<RoleEntity> roles = user.getRoles();
		for (RoleEntity role : roles) {
			if (role.getName() == ERole.ROLE_ENABLER) {
				return true;
			}
		}
		return false;
	}

	@Override
	public LearnersResponse getLearnerById(Long learnerId) {
		UserEntity learner = repository.findById(learnerId)
				.orElseThrow(() -> new RuntimeException("Id not found" + learnerId));

		return new LearnersResponse(learner);
	}

	@Override
	public EnablerResponse getEnablerById(Long enablerId) {
		UserEntity user = repository.findById(enablerId)
				.orElseThrow(() -> new RuntimeException("Id not found " + enablerId));
		return new EnablerResponse(user);
	}

	@Override
	public List<EnablerResponse> getAllEnablers() {
		List<UserEntity> allUsers = repository.findAll();
		List<UserEntity> allEnablers = new ArrayList<>();
		for (UserEntity enabler : allUsers) {
			if (userHasEnablerRole(enabler)) {
				allEnablers.add(enabler);
			}
		}
		return allEnablers.stream().map(EnablerResponse::new).collect(Collectors.toList());
	}

	@Override
	public List<EnablerResponse> getAllEnablers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		return null;
	}

	@Override
	public void addLearnerInfoR(Long learnerId, LearnersRequest request) {
		try {
			UserEntity user = repository.findById(learnerId).get();
			repository.save(user);

		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}

	}

	@Override
	public void verifyLearner(Long learnersId) {
		try {
			UserEntity learner = repository.findById(learnersId)
					.orElseThrow(() -> new RuntimeException("Error occured " + learnersId));
				learner.setIsVerified(true);
				repository.save(learner);
			
		} catch (Exception e) {
			throw new RuntimeException("Error occured " + e.getMessage());
		}
	}
}
