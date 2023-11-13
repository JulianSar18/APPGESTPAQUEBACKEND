package com.tc_28.api.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tc_28.api.dto.PackageDto;
import com.tc_28.api.dto.PackageToDeliverDto;
import com.tc_28.api.dto.VehicleDriverDto;
import com.tc_28.api.model.PackageM;
import com.tc_28.api.model.StatusTrip;
import com.tc_28.api.model.TripM;
import com.tc_28.api.model.VehicleTripM;
import com.tc_28.api.model.ZoneM;
import com.tc_28.api.repository.PackageRepository;
import com.tc_28.api.repository.TripRepository;
import com.tc_28.api.repository.VehicleRepository;
import com.tc_28.api.repository.VehicleTripRepository;
import com.tc_28.api.repository.ZoneRespository;



@Service
public class PackageServiceImpl implements PackageService {
	
	@Autowired
	private PackageRepository packageRepository;
	@Autowired
	private TripRepository tripRepository;
	@Autowired
	private ZoneRespository zoneRespository;	
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private VehicleTripRepository vehicleTripRepository;
	
	@Override
	public PackageDto addInventory(PackageDto packageDto) {
		boolean added = false;
		ZoneM zone = ZoneM.builder().id(packageDto.getId_zone()).build();
		List<TripM> checkTrip = tripRepository.checkTrip(StatusTrip.PENDIENTE.name());
		if (checkTrip.size() != 0) {
			for (TripM trip : checkTrip) {
				List<PackageM> packages = trip.getPackages();
				if (!packages.isEmpty() && packages.size() < 10 && packages.get(0).getZone().getId() == packageDto.getId_zone()) {
					TripM tripSaved = tripRepository.save(trip);
					PackageM packageM = PackageM.builder().category(packageDto.getCategory())
							.zone(zone).trip(tripSaved).build();
					packageRepository.save(packageM);					
					added = true;
					break;
				}
			}
			if(!added) {
				createTrip(packageDto, zone);
			}
		} else {
			createTrip(packageDto, zone);
		}

		return packageDto;
	}	
	private void createTrip(PackageDto packageDto, ZoneM zone) {
		List<PackageM> packagesL = new ArrayList<>();
		Date date = new Date();
		PackageM packageM = PackageM.builder().category(packageDto.getCategory()).zone(zone).build();
		packagesL.add(packageM);
		TripM trip = TripM.builder().date(date).status(StatusTrip.PENDIENTE.name()).capacity(10).packages(packagesL).build();
		TripM tripSaved = tripRepository.save(trip);
		packageM.setTrip(tripSaved);
		packageRepository.save(packageM);
	}
	

	@Override
	public String updateStatusPackage(PackageToDeliverDto packageToDeliverDto, String id_driver) {
		PackageM packageM = packageRepository.getReferenceById(packageToDeliverDto.getId());
		packageM.setStatus(1);
		PackageM uptadePackage = packageRepository.save(packageM);
		int pendingPackages = tripRepository.checkPendingPackages(uptadePackage.getTrip().getId());
		if(pendingPackages == 0) {
			VehicleTripM vehicleTrip =vehicleTripRepository.licensePlateByIdTrip(uptadePackage.getTrip().getId());
			VehicleDriverDto vehicleDto = VehicleDriverDto.builder()
				.license_plate(vehicleTrip.getLicense_plate())
				.identification_card(id_driver)
				.build();
			tripRepository.updateStateTrip(StatusTrip.COMPLETADO.name(), uptadePackage.getTrip().getId());
			vehicleRepository.updateVehicleTraveling(vehicleDto, 0);
			
		}
		return packageToDeliverDto.getName() + " fue entregado correctamente";
	}

	@Override
	public String saveMassivePackages(MultipartFile file) throws IOException {
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
				String zone_name = infoRow.get(0);
				int id_zone = zoneRespository.getIdSpecificZone(zone_name);				
				PackageDto packageDto = PackageDto.builder()
						.id_zone(id_zone)
						.category(infoRow.get(1))
						.build();				
				addInventory(packageDto);
				infoRow.clear();
			}		
		}catch (Exception e) {
			return e.getMessage();
		}
		return "Los paquetes fueron cargados Correctamente";
	}

}
