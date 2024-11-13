package com.lgs.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lgs.api.request.UserDTO;
import com.lgs.api.response.MessageResponse;
import com.lgs.api.service.SponsorService;

@RestController
@RequestMapping("/api/v1/sponsor")
@CrossOrigin(origins = "*")
public class SponsorshipController {

	@Autowired
	private SponsorService sponsorService;

	@PutMapping("/{enablerId}")
	public ResponseEntity<?> mapSponsor(@PathVariable("enablerId") Long enablerId,
			@RequestBody List<UserDTO> learners) {
		try {
			sponsorService.sponsorLearner(enablerId, learners);
			return new ResponseEntity<>(new MessageResponse("Mapped successfully" ), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageResponse("Error occured " + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/enabler/{enablerId}")
	public ResponseEntity<?> findBySponsorId(@PathVariable("enablerId") Long enablerId) {
		try {
			return new ResponseEntity<>(sponsorService.findAllLearnersBySponsorId(enablerId), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(new MessageResponse("Error occured "+e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

}
