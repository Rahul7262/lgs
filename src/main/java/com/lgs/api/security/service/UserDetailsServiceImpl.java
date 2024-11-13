package com.lgs.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgs.api.entity.UserEntity;
import com.lgs.api.repository.UserRepository;

/**
 * @author Shahzeb Khan
 *
 * 
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	  @Autowired
	  UserRepository userRepository;

	  @Override
	  @Transactional
	  public UserDetails loadUserByUsername(String email)  {
	    UserEntity user = userRepository.findByEmail(email)
	        .orElseThrow(() -> new RuntimeException("User Not Found with username: " + email));

	    return UserDetailsImpl.build(user);
	  }

}

