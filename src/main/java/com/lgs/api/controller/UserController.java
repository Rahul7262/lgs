package com.lgs.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgs.api.request.AssignProgramRequest;
import com.lgs.api.request.EnablersRequest;
import com.lgs.api.request.LearnersRequest;
import com.lgs.api.request.UserDTO;
import com.lgs.api.request.UserRequest;
import com.lgs.api.response.EnablerResponse;
import com.lgs.api.response.LearnersPageResponse;
import com.lgs.api.response.LearnersResponse;
import com.lgs.api.response.MessageResponse;
import com.lgs.api.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@PutMapping("/activate/{userId}")
	public ResponseEntity<?> activateUser(@PathVariable("userId") Long userId) {
		try {
			userService.activateUser(userId);
			Map<String, Object> responseObject = new HashMap<>();
			responseObject.put("message", "Learner activated successfully");
			responseObject.put("httpStatus", 200);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("Error occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/deactivate/{userId}")
	public ResponseEntity<?> deactivateUser(@PathVariable("userId") Long userId) {
		try {
			userService.deactivateUser(userId);
			Map<String, Object> responseObject = new HashMap<>();
			responseObject.put("message", "Learner deactivated successfully");
			responseObject.put("httpStatus", 200);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("Error occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/learners/{learnerId}")
	public ResponseEntity<?> updateLearner(@PathVariable("learnerId") Long learnerId,
			@RequestBody LearnersRequest learner) {
		try {
			userService.updateLearner(learnerId, learner);
			return new ResponseEntity<>(new MessageResponse("Learner updated successfully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("Error occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/enablers/{email}")
	public ResponseEntity<?> updateEnabler(@PathVariable("enablerId") Long enablerId,
			@RequestBody EnablersRequest enabler) {
		try {
			userService.updateEnabler(enablerId, enabler);
			return new ResponseEntity<>(new MessageResponse("Enabler updated successfully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("Error occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/learners/get/all")
	public ResponseEntity<?> getAllLearners(@RequestParam(defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(defaultValue = "50", required = false) Integer pageSize,
			@RequestParam(defaultValue = "", required = false) String searchBy,
			@RequestParam(defaultValue = "", required = false) String searchTerm,
			@RequestParam(defaultValue = "id", required = false) String sortBy,
			@RequestParam(defaultValue = "asc", required = false) String sortDir) {
		try {
			List<LearnersResponse> learners = userService.getAllLearners(pageNumber, pageSize, sortBy, sortDir,
					searchBy, searchTerm);

			LearnersPageResponse response = new LearnersPageResponse(pageNumber, pageSize, searchBy, searchTerm, sortBy,
					sortDir.equalsIgnoreCase("asc") ? 1 : -1, new Date(), new Date(), learners);
//            Map<String, Object> responseObject = new HashMap<>();
//            responseObject.put("message", "Success");
//            responseObject.put("httpStatus", 200);
//            responseObject.put("learners", response);

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error occurred: " + e.getMessage()));
		}
	}

	@PostMapping("/assign/learner/{userId}")
	public ResponseEntity<?> addPrograms(@PathVariable Long userId, @RequestBody AssignProgramRequest request) {
		try {
			userService.addPrograms(userId, request);
			Map<String, Object> responseObject = new HashMap<>();
			responseObject.put("message", "program assigned successfully");
			responseObject.put("status", 200);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception e) {
			Map<String, Object> responseObject = new HashMap<>();
			responseObject.put("message", "program not assigned "+e.getMessage());
			responseObject.put("status", 500);
			return new ResponseEntity<>(responseObject,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/map/{enablerId}")
	public ResponseEntity<?> sponsorLearners(@PathVariable Long enablerId, @RequestBody List<UserDTO> learners) {
		try {
			userService.sponsorLearner(enablerId, learners);
			return new ResponseEntity<>(new MessageResponse("Mapped successfully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("Error occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/learner/additionalInfo/{learnersId}")
	public ResponseEntity<?> addLernerInfo(@PathVariable("learnersId") Long learnersId,
			@RequestBody LearnersRequest request) {
		try {
			userService.addLearnerInfo(learnersId, request);
			return new ResponseEntity<>(new MessageResponse("Learner info added successfully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("Error occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/learner/{learnerId}")
	public ResponseEntity<?> getLearnerById(@PathVariable Long learnerId) {
		try {
			return new ResponseEntity<>(userService.getLearnerById(learnerId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("Error occured " + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/enablers/{id}")
	public ResponseEntity<EnablerResponse> getEnablerById(@PathVariable Long id) {
		EnablerResponse enablerResponse = userService.getEnablerById(id);
		return ResponseEntity.ok(enablerResponse);
	}

	@PostMapping("/enablers/{id}")
	public ResponseEntity<?> addEnablerInfo(@PathVariable("id") Long id, @RequestBody EnablersRequest request) {
		try {
			userService.addEnablerInfo(id, request);
			return new ResponseEntity<>(new MessageResponse("Enabler added"), HttpStatus.OK);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/enablers/find-all")
	public ResponseEntity<?> findAllEnablers() {
		try {
			return new ResponseEntity<>(userService.getAllEnablers(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("Error occured " + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/learner/additionalInfoR/{learnersId}")
	public ResponseEntity<?> addLernerInfoR(@PathVariable("learnersId") Long learnersId,
			@RequestBody LearnersRequest request) {
		try {
			userService.addLearnerInfoR(learnersId, request);
			return new ResponseEntity<>(new MessageResponse("Learner info added successfully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("Error occurred: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/verfify/{learnersId}")
	public ResponseEntity<?> verifyLearner(@PathVariable("learnersId") Long learnerId) {
		try {
			 userService.verifyLearner(learnerId);
			Map<String, Object> responseObject = new HashMap<>();
			responseObject.put("message", "Learner verified successfully");
			responseObject.put("httpStatus", 200);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("Error occured " + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

}
