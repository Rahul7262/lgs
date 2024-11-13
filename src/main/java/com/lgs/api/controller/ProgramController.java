package com.lgs.api.controller;

import java.util.HashMap;
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

import com.lgs.api.request.ProgramRequest;
import com.lgs.api.response.MessageResponse;
import com.lgs.api.service.ProgramService;

@RestController
@RequestMapping("/api/v1/program")
@CrossOrigin(origins = "*")
public class ProgramController {

	@Autowired
	private ProgramService service;

	@PostMapping("/")
	public ResponseEntity<?> addProgram(@RequestBody ProgramRequest request) {
		try {
			service.addProgram(request);
			return new ResponseEntity<>(new MessageResponse("Program added successufully"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("Error occured " + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{programId}")
	public ResponseEntity<?> addLearners(@PathVariable("programId") Long programId,
			@RequestBody ProgramRequest request) {
		try {
			service.addLearner(programId, request);
			return new ResponseEntity<>(new MessageResponse("Learner added to the program successfully"),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("Error occured" + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/")
    public ResponseEntity<?> findAllPrograms(@RequestParam(defaultValue = "0", required = false) Integer pageNumber ,
                                             @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                             @RequestParam(defaultValue = "programId", required = false)String sortBy,
                                             @RequestParam(defaultValue = "asc", required = false)String sortDir) {
        try {
            return new ResponseEntity<>(service.getAllPrograms(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Error occurred: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
	
	   @PutMapping("/activate/{programId}")
	    public ResponseEntity<?> activateProgram(@PathVariable Long programId) {
	        try {
	        	service.activateProgram(programId);
	        	Map<String, Object> responseObject = new HashMap<>();
				responseObject.put("message", "program activated successfully");
				responseObject.put("httpStatus", 200);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
	        } catch (Exception e) {
	            throw new RuntimeException("Error occurred: " + e.getMessage());
	        }
	    }

	    @PutMapping("/deactivate/{programId}")
	    public ResponseEntity<?> deactivateProgram(@PathVariable Long programId) {
	        try {
	        	service.deactivateProgram(programId);
	        	Map<String, Object> responseObject = new HashMap<>();
				responseObject.put("message", "Learner deactivated successfully");
				responseObject.put("httpStatus", 200);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
	        } catch (Exception e) {
	            throw new RuntimeException("Error occurred: " + e.getMessage());
	        }
	    }
	
	
}
