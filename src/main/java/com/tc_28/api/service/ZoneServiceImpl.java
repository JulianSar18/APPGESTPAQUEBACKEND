package com.tc_28.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tc_28.api.model.ZoneM;
import com.tc_28.api.repository.ZoneRespository;

@Service
public class ZoneServiceImpl implements ZoneService{
	@Autowired
	private ZoneRespository zoneRespository;	
	@Override
	public List<ZoneM> getZones() {
		List<ZoneM> zones = zoneRespository.findAll();
		return zones;
	}

}
