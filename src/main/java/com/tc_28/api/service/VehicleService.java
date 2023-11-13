package com.tc_28.api.service;

import com.tc_28.api.dto.VehicleDriverDto;
import com.tc_28.api.dto.VehicleTripAssigmentDto;
import com.tc_28.api.model.VehicleM;

public interface VehicleService {
	public VehicleM updateVehicle(VehicleDriverDto vehicleDriver);
	public String assignTriptoVehicle(VehicleTripAssigmentDto vehicle) throws Exception;
}
