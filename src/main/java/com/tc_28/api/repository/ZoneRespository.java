package com.tc_28.api.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tc_28.api.model.ZoneM;

public interface ZoneRespository extends JpaRepository<ZoneM, Integer>{
	@Query("SELECT z FROM ZoneM z")
	public List<ZoneM> getZones();
	@Query(value ="SELECT z.ID FROM ZONES z WHERE z.ZONE_NAME = ?1", nativeQuery = true )
	public Integer getIdSpecificZone(String zone_name);
}
