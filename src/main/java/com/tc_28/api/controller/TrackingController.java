package com.tc_28.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tc_28.api.dto.PackageToDeliverDto;
import com.tc_28.api.dto.VehiclePackagesDto;
import com.tc_28.api.service.PackageService;
import com.tc_28.api.service.TripService;

@RestController
@CrossOrigin
@RequestMapping("/api/tracking")
public class TrackingController {
	@Autowired
	private TripService tripService;
	@Autowired
	private PackageService packageService;
	@GetMapping("/getVehiclePackages")
	public ResponseEntity<Object> getVehiclePackages(@RequestParam(required = true) String id_driver){
		VehiclePackagesDto vehiclePackages =tripService.getpackages(id_driver);
		return new ResponseEntity<>(vehiclePackages, HttpStatus.OK);
	}
	@PutMapping("/checkDelivery")
	public ResponseEntity<Object> uptadePackageStatus(@RequestBody(required = true) PackageToDeliverDto packageToDeliverDto, @RequestParam(required = true) String id_driver){
		String result = packageService.updateStatusPackage(packageToDeliverDto, id_driver);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
