package com.tc_28.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tc_28.api.dto.TripDto;
import com.tc_28.api.dto.VehicleDriverDto;
import com.tc_28.api.dto.VehicleTripAssigmentDto;
import com.tc_28.api.model.VehicleM;
import com.tc_28.api.service.TripService;
import com.tc_28.api.service.VehicleDriverService;
import com.tc_28.api.service.VehicleService;

@RestController
@CrossOrigin
@RequestMapping("/api/operational")
public class OperationalController {
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private VehicleDriverService vehicleDriverService;
	@Autowired
	private TripService tripService;
	@PutMapping("/driverEntry")
	public ResponseEntity<Object> updateVehicleIncome(@RequestBody(required = true) VehicleDriverDto vehicleDriver){
		//
		VehicleM vehicleAffected = vehicleService.updateVehicle(vehicleDriver);
		return new ResponseEntity<>(vehicleAffected, HttpStatus.OK);
	}
	
	@GetMapping("/getVehicleEntered")
	public ResponseEntity<Object> getVehiclesEntered(@RequestParam(required = true) int page, @RequestParam(required = false) String id_driver){
		Page<VehicleDriverDto> vehiclesEntered =  vehicleDriverService.getVehicleDriversEntered(page, id_driver);
		return new ResponseEntity<>(vehiclesEntered, HttpStatus.OK);
	}
	
	@GetMapping("/getTripsVehicleZone")
	public ResponseEntity<Object> getTripsVehicleZone(String license_plate){
		List<TripDto> trips = tripService.getTripsVehicleZone(license_plate);
		return new ResponseEntity<>(trips, HttpStatus.OK);
	}
	
	@PutMapping("/loadTrip")
	public ResponseEntity<Object> loadTrip(@RequestBody(required = true) VehicleTripAssigmentDto vehicletrip) throws Exception{
		try {
			String assign = vehicleService.assignTriptoVehicle(vehicletrip);
			return new ResponseEntity<>(assign, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}		
		
	}
}
