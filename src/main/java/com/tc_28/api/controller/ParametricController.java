package com.tc_28.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tc_28.api.dto.CommissionDto;
import com.tc_28.api.dto.ContractorDto;
import com.tc_28.api.dto.PackageDto;
import com.tc_28.api.dto.VehicleDriverDto;
import com.tc_28.api.service.CommissionService;
import com.tc_28.api.service.ContractorService;
import com.tc_28.api.service.PackageService;
import com.tc_28.api.service.VehicleDriverService;

@RestController
@CrossOrigin
@RequestMapping("/api/parametric")
public class ParametricController {
	@Autowired
	private ContractorService contractorService;
	@Autowired
	private VehicleDriverService vehicleDriverService;
	@Autowired
	private CommissionService commissionService;
	@Autowired
	private PackageService packageService;
	@PostMapping("/contractor")
	public ResponseEntity<Object> addContractor(@RequestBody ContractorDto contractor){
		ContractorDto addedContractor = contractorService.addContractor(contractor);
		return new ResponseEntity<>(addedContractor, HttpStatus.OK);
	}
	@PostMapping("/vehicleDriver")
	public ResponseEntity<Object> addVehicleDriver(@RequestBody(required = true) VehicleDriverDto vehicleDriver){
		VehicleDriverDto addedVehicleDriver = vehicleDriverService.save(vehicleDriver);
		return new ResponseEntity<>(addedVehicleDriver, HttpStatus.OK);
	}
	
	@PostMapping("/commission")
	public ResponseEntity<Object> addCommission(@RequestBody CommissionDto commission){
		CommissionDto addedCommission = commissionService.createCommission(commission);
		return new ResponseEntity<>(addedCommission, HttpStatus.OK);
	}
	
	@PostMapping("/inventory")
	public ResponseEntity<Object> addInventory(@RequestBody PackageDto packageD){
		PackageDto addedPackage = packageService.addInventory(packageD);
		return new ResponseEntity<>(addedPackage, HttpStatus.OK);
	}
	
	@PostMapping("/upload/vehicleDrivers")
	public ResponseEntity<Object>  uploadVehicleDrivers(@RequestParam MultipartFile file) throws Exception{
		String upload = vehicleDriverService.saveMassiveVehicleDrivers(file);
		return new ResponseEntity<>(upload, HttpStatus.OK);
    }
	
	@PostMapping("/upload/inventory")
	public ResponseEntity<Object>  uploadInventory(@RequestParam MultipartFile file) throws Exception{
		String upload = packageService.saveMassivePackages(file);
		return new ResponseEntity<>(upload, HttpStatus.OK);
    }

}
