package com.tc_28.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tc_28.api.dto.ContractorDto;
import com.tc_28.api.dto.SummaryTrip;
import com.tc_28.api.dto.VehicleDriverDto;
import com.tc_28.api.model.ZoneM;
import com.tc_28.api.service.ContractorService;
import com.tc_28.api.service.TripService;
import com.tc_28.api.service.VehicleDriverService;
import com.tc_28.api.service.ZoneService;

@RestController
@CrossOrigin
@RequestMapping("/api/summary")
public class SummaryController {
	@Autowired
	private ZoneService zoneService;
	@Autowired
	private ContractorService contractorService;
	@Autowired
	private VehicleDriverService vehicleDriverService;
	@Autowired
	private TripService tripService;
	
	@GetMapping("/getZones")
	public ResponseEntity<Object> getZones(){
		List<ZoneM> zones = zoneService.getZones();
		return new ResponseEntity<>(zones, HttpStatus.OK);
	}
	
	@GetMapping("/getCompanies")
	public ResponseEntity<Object> getCompanies(){
		List<ContractorDto> contractors = contractorService.getContractors();
		return new ResponseEntity<>(contractors, HttpStatus.OK);
	}
	
	@GetMapping("/getVehicleDrivers")
	public ResponseEntity<Object> getVehicleDrivers(@RequestParam(required = false) String driver_id){
		List<VehicleDriverDto> vehicleDrivers = vehicleDriverService.getVehicleDrivers(driver_id);		
		return new ResponseEntity<>(vehicleDrivers, HttpStatus.OK);
	}
	
	@GetMapping("/getCompaniesWithOutCommission")
	public ResponseEntity<Object> getCompaniesWithOutCommission(){
		List<ContractorDto> contractors = contractorService.getContractorsWithOuthCommission();
		return new ResponseEntity<>(contractors, HttpStatus.OK);
	}
	
	@GetMapping("/getCompany")
	public ResponseEntity<Object> getCompany(@RequestParam(required = true) String nit){
		ContractorDto contractors = contractorService.getContractorById(nit);
		return new ResponseEntity<>(contractors, HttpStatus.OK);
	}
	
	@GetMapping("/getAllTrips")
	public ResponseEntity<Object> getSummaryAllTrips(@RequestParam(required = true)int page, @RequestParam(required = false) String id_driver){
		Page<SummaryTrip> summaryTrips = tripService.getAllTrips(page, id_driver);
		return new ResponseEntity<>(summaryTrips, HttpStatus.OK);
	}
}
