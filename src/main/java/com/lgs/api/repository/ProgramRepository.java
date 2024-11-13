package com.lgs.api.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lgs.api.entity.ProgramEntity;

@Repository
public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {

	@Modifying
	@Transactional
	@Query("UPDATE ProgramEntity p SET p.isActive = false WHERE p.programId = :programId")
	public void deactivateisActiveByProductId(@Param("programId")Long programId);
	
	@Modifying
	@Transactional
	@Query("UPDATE ProgramEntity p SET p.isActive = true WHERE p.programId = :programId")
	public void activateisActiveByProductId(@Param("programId")Long programId);
	
}
