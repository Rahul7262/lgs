package com.lgs.api.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lgs.api.entity.UserEntity;

/**
 * @author Shahzeb Khan
 *
 * 
 */

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String email);

	Boolean existsByEmail(String email);

	@Modifying
	@Transactional
	@Query("UPDATE UserEntity u SET u.isActive = false WHERE u.id = :id")
	public void deactivateisActiveById(@Param("id") Long id);

	@Modifying
	@Transactional
	@Query("UPDATE UserEntity u SET u.isActive = true WHERE u.id = :id")
	public void activateisActiveById(@Param("id") Long id);

	@Query("SELECT u.sponseredLearners FROM UserEntity u WHERE u.id = :enablerId")
	List<UserEntity> findAllLearnersByEnablerId(@Param("enablerId") Long enablerId);

	Page<UserEntity> findByUsernameContaining(String searchTerm, Pageable pageable);
}
