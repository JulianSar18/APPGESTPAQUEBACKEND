package com.tc_28.api.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tc_28.api.dto.VehicleDriverDto;
import com.tc_28.api.model.ContractorM;
import com.tc_28.api.model.ContractorVehicleM;
import com.tc_28.api.model.DriverM;
import com.tc_28.api.model.VehicleM;
import com.tc_28.api.repository.ContractorRepository;
import com.tc_28.api.repository.ContractorVehicleRepository;
import com.tc_28.api.repository.DriverRepository;
import com.tc_28.api.repository.VehicleRepository;

@Service
public class VehicleDriverServiceImpl implements VehicleDriverService {
	@Autowired
	private DriverRepository driverRepository;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private ContractorVehicleRepository contractorVehicleRepository;
	@Autowired
	private ContractorRepository contractorRepository;
	@Override
	public VehicleDriverDto save(VehicleDriverDto vehicleDriver) {

		DriverM driver = DriverM.builder().identification_card(vehicleDriver.getIdentification_card())
				.name(vehicleDriver.getDriver_name()).build();

		VehicleM vehicle = VehicleM.builder().license_plate(vehicleDriver.getLicense_plate().toUpperCase()).income(0).id_zone(0)
				.id_driver(vehicleDriver.getIdentification_card()).build();

		ContractorVehicleM contractorVehicle = ContractorVehicleM.builder().nit(vehicleDriver.getNit())
				.license_plate(vehicleDriver.getLicense_plate()).build();

		driverRepository.addDriver(driver);
		vehicleRepository.addVehicle(vehicle);
		contractorVehicleRepository.addContractorVehicle(contractorVehicle);

		return vehicleDriver;
	}

	@Override
	public List<VehicleDriverDto> getVehicleDrivers(String driver_id) {
		List<VehicleDriverDto> vehicleDrivers = (driver_id == null || driver_id.isEmpty()) ? vehicleRepository.findAllVehicleDriverNotEntered()
				: vehicleRepository.findVehicleDriverByDriverId(driver_id);

		return vehicleDrivers;
	}

	@Override
	public String saveMassiveVehicleDrivers(MultipartFile file) throws IOException {
			ContractorM contractor = new ContractorM();
			DriverM driverM = new DriverM();	
			List<VehicleDriverDto> vehicleDriver = new ArrayList<>(); 
			List<String> infoRow = new ArrayList<>();
			InputStream reader = file.getInputStream();
			try (Workbook workbook = new HSSFWorkbook(reader)) {
				Sheet sheet = workbook.getSheetAt(0);
				DataFormatter dataFormatter = new DataFormatter();
				Iterator<Row> rowIterator = sheet.rowIterator();
				while(rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if(row.getRowNum()== 0) continue;
					Iterator<Cell> cellIterator = row.cellIterator();
					while(cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);						
						infoRow.add(cellValue);						
					}
					vehicleDriver = vehicleRepository.findVehicleDriverByDriverId(infoRow.get(3));
					if(vehicleDriver.isEmpty()) {

						contractor = contractorRepository.findContractorById(infoRow.get(0));
						if(contractor.getNit() == null) {
							contractor = ContractorM.builder()
									.nit(infoRow.get(0))
									.company_name(infoRow.get(1))
									.build();
							contractorRepository.addContractor(contractor);
						}
						driverM = driverRepository.getDriverById(infoRow.get(3));
						if(driverM.getIdentification_card() != null) {
							infoRow.clear();
							continue;
						}
						DriverM driver = DriverM.builder().identification_card(infoRow.get(3))
								.name(infoRow.get(4)).build();

						VehicleM vehicle = VehicleM.builder()
								.license_plate(infoRow.get(2).toUpperCase())
								.income(0)
								.id_zone(0)
								.id_driver(infoRow.get(3))
								.build();

						ContractorVehicleM contractorVehicle = ContractorVehicleM.builder()
								.nit(contractor.getNit())
								.license_plate(infoRow.get(2))
								.build();
						
						driverRepository.addDriver(driver);
						vehicleRepository.addVehicle(vehicle);
						contractorVehicleRepository.addContractorVehicle(contractorVehicle);
						infoRow.clear();
					}else if(vehicleDriver.get(0).getLicense_plate() != null) {
						infoRow.clear();
						continue;
					}
				}
			}
			return "Datos Cargados correctamente";
	}

	@Override
	public Page<VehicleDriverDto> getVehicleDriversEntered(int page, String id_driver) {
		Pageable pageable = PageRequest.of(page - 1, 5); 
		return (id_driver == null || id_driver.isEmpty()) ? vehicleRepository.findAllVehicleDriverEntered(pageable) : vehicleRepository.findVehicleDriverEnteredByIdDriver(pageable, id_driver);
	}

}
