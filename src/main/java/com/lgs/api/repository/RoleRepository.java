package com.lgs.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lgs.api.entity.RoleEntity;
import com.lgs.api.util.ERole;

/**
 * 
 * @author Shahzeb Khan
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	Optional<RoleEntity> findByName(ERole name);
}
