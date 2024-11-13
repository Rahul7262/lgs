package com.lgs.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class IdType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTypeId;
	
	private String idTypeName;
}
