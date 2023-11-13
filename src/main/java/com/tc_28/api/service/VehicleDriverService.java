package com.tc_28.api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.tc_28.api.dto.VehicleDriverDto;

public interface VehicleDriverService {
	public VehicleDriverDto save(VehicleDriverDto vehicleDriver);
	public List<VehicleDriverDto> getVehicleDrivers(String driver_id);
	public String saveMassiveVehicleDrivers(MultipartFile file) throws IOException;
	public Page<VehicleDriverDto> getVehicleDriversEntered(int page, String id_driver);
}
