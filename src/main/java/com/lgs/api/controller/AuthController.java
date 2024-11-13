package com.lgs.api.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lgs.api.entity.RoleEntity;
import com.lgs.api.entity.UserEntity;
import com.lgs.api.repository.RoleRepository;
import com.lgs.api.repository.UserRepository;
import com.lgs.api.request.LoginRequest;
import com.lgs.api.request.SignupRequest;
import com.lgs.api.response.JwtResponse;
import com.lgs.api.response.MessageResponse;
import com.lgs.api.security.util.JwtTokenUtill;
import com.lgs.api.util.ERole;

/**
 * @author Shahzeb Khan
 *
 * 
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtTokenUtill jwtTokenUtill;
	
	

	@PostMapping("/v1/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
//		UserEntity userByEmail = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new RuntimeException("Bad creadentials")); 
//		if(userByEmail.getIsActive() == true) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		JwtResponse jwt = jwtTokenUtill.generateJwtToken(authentication);

		return ResponseEntity.ok((jwt));
		}
//		else {
//			return new ResponseEntity<>(new MessageResponse("Bad Credentials"), HttpStatus.FORBIDDEN);
	

	@PostMapping("/v1/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		UserEntity user = new UserEntity(signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRoles();
		Set<RoleEntity> roles = new HashSet<>();

//		if (strRoles == null) {
//			RoleEntity learnerRole = roleRepository.findByName(ERole.ROLE_LEARNER)
//					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
////			user.setUsername(signUpRequest.getLearner().getLearnersName());
////		    user.setEmail(signUpRequest.getLearner().getLearnersEmail());
////		    user.setUserMobileNo(signUpRequest.getLearner().getLearnersMobileNo());
////		    user.setUserAddress(signUpRequest.getLearner().getLearnersAddress());
////		    user.setUsersGender(signUpRequest.getLearner().getLearnersGender());
////		    user.setUserImageUrl(signUpRequest.getLearner().getLearnersImageUrl());
////		    user.setUserIdType(signUpRequest.getLearner().getLearnersIdType());
////		    user.setUserIdentity(signUpRequest.getLearner().getLearnersIdentity());
////		    user.setContactPersonName(signUpRequest.getLearner().getContactPersonName());
////		    user.setContactPersonMobileNo(signUpRequest.getLearner().getContactPersonMobileNo());	    
////		    user.setContactPersonEmail(signUpRequest.getLearner().getContactPersonEmail());
//			roles.add(learnerRole);
//		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					
					roles.add(adminRole);
					break;
				case "enabler":
					RoleEntity enablerRole = roleRepository.findByName(ERole.ROLE_ENABLER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(enablerRole);
					break;
				case "learner":
					RoleEntity learnerRole = roleRepository.findByName(ERole.ROLE_LEARNER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(learnerRole);
			break;
				}
			});
//		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostConstruct
	public ResponseEntity<?> saveRoles() {
		try {
			if (roleRepository.findAll().isEmpty()) {
				List<RoleEntity> roles = new ArrayList<>();
				roles.add(new RoleEntity(ERole.ROLE_ADMIN));
				roles.add(new RoleEntity(ERole.ROLE_LEARNER));
				roles.add(new RoleEntity(ERole.ROLE_ENABLER));
				
				roleRepository.saveAll(roles);
			}
		} catch (Exception e) {

			return new ResponseEntity<>(new MessageResponse("Error occured"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new MessageResponse("Sucess"), HttpStatus.OK);

	}

}
