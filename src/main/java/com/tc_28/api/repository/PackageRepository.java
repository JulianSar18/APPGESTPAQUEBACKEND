package com.tc_28.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.tc_28.api.model.PackageM;

public interface PackageRepository extends JpaRepository<PackageM, Integer>{	
	
}
