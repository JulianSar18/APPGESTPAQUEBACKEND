package com.tc_28.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tc_28.api.model.CommissionM;

public interface CommissionRepository extends JpaRepository<CommissionM, Integer>{
	
}
