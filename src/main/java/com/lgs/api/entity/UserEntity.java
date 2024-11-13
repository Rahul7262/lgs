package com.lgs.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Shahzeb Khan
 *
 * 
 */

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
@Setter
@Getter
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

//	@NotBlank
	private String username;

//	@NotBlank
	private String userMobileNo;

//	@NotBlank
	private String userAddress;

//	private String tutorQualification;
//
//	private String tutorExperince;

//	@NotBlank
	private String usersGender;

	private String userImageUrl;

//	@NotBlank
	private String userIdType;

//	@NotBlank
	private String userIdentity;

	private String updatedBy;

	private String qualification;

	private String userGST;

	private String contactPersonName;

	private String contactPersonMobileNo;

	private String contactPersonEmail;

	private Boolean isActive = true;

	private Boolean isVerified;

	private Boolean isProject;

	private Boolean isDetailsFilled = false;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "SponserdLearners", joinColumns = @JoinColumn(name = "enabler_id"), inverseJoinColumns = @JoinColumn(name = "learner_id"))
	private List<UserEntity> sponseredLearners = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "sponsorId")
	private List<Sponsorship> sponsor = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "program_users", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "program_id"))
	private List<ProgramEntity> program = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleEntity> roles = new HashSet<>();

	public UserEntity() {
	}

	public UserEntity(String email, String password) {

		this.email = email;
		this.password = password;
	}

}
