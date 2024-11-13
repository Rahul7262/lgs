package com.lgs.api.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ProgramEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long programId;
	
	private String programName;
	
	private String programDescription;
	
	private String programDuration;
	
	private Double programFee;
	
	private Integer participantCount = 0;
	
	private Boolean isActive;
	
	@ManyToMany(mappedBy = "program")
	private List<UserEntity> learners = new ArrayList<>();
	
}
