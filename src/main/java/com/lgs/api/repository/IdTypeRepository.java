package com.lgs.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgs.api.entity.IdType;

public interface IdTypeRepository extends JpaRepository<IdType, Long> {

}
