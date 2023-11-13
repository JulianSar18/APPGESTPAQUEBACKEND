package com.tc_28.api.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tc_28.api.dto.SummaryTrip;
import com.tc_28.api.dto.TripDto;
import com.tc_28.api.dto.VehiclePackagesDto;

public interface TripService {
	public List<TripDto> getTripsVehicleZone(String license_plate);
	public VehiclePackagesDto getpackages(String idDriver);
	public Page<SummaryTrip> getAllTrips(int pageNumber, String id_driver);
}
