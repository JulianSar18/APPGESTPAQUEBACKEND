package com.tc_28.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tc_28.api.dto.VehicleDriverDto;
import com.tc_28.api.dto.VehicleTripAssigmentDto;
import com.tc_28.api.model.StatusTrip;
import com.tc_28.api.model.VehicleM;
import com.tc_28.api.model.VehicleTripM;
import com.tc_28.api.repository.TripRepository;
import com.tc_28.api.repository.VehicleRepository;
import com.tc_28.api.repository.VehicleTripRepository;
@Service
public class VehicleServiceImpl implements VehicleService {
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private TripRepository tripRepository;
	@Autowired
	private VehicleTripRepository vehicleTripRepository;
	@Override
	public VehicleM updateVehicle(VehicleDriverDto vehicleDriver) {
		VehicleM vehicleAffected = vehicleRepository.updateVehicleIncome(vehicleDriver);
		return vehicleAffected;
	}
	@Override
	public String assignTriptoVehicle(VehicleTripAssigmentDto vehicle) throws Exception {
		if(vehicle.getLicense_plate() == null) throw new Exception("Argumento de entrada nulo " + vehicle);
		VehicleTripM vehicleTripM = VehicleTripM.builder()
				.license_plate(vehicle.getLicense_plate())
				.id_trip(vehicle.getId_trip())
				.build();
		VehicleDriverDto vehicleDriver = VehicleDriverDto.builder()
				.driver_name(vehicle.getDriver_name())
				.identification_card(vehicle.getIdentification_card())
				.license_plate(vehicle.getLicense_plate())
				.nit(vehicle.getNit())
				.build();
		try {
			vehicleRepository.updateVehicleTraveling(vehicleDriver, 3);
			vehicleTripRepository.addVehicleTrip(vehicleTripM);
			tripRepository.updateStateTrip(StatusTrip.VIAJANDO.name(), vehicle.getId_trip());
		}catch (Exception e) {
			return e.getMessage();
		}
		
		return "Viaje asignado a la placa " + vehicle.getLicense_plate();
	}

}
