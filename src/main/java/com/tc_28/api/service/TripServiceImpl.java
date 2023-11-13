package com.tc_28.api.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tc_28.api.dto.PackageToDeliverDto;
import com.tc_28.api.dto.SummaryTrip;
import com.tc_28.api.dto.TripDto;
import com.tc_28.api.dto.VehicleDriverDto;
import com.tc_28.api.dto.VehiclePackagesDto;
import com.tc_28.api.model.StatusTrip;
import com.tc_28.api.repository.TripRepository;
import com.tc_28.api.repository.VehicleRepository;

@Service
public class TripServiceImpl implements TripService{
	@Autowired
	private TripRepository tripRepository;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Override
	public List<TripDto> getTripsVehicleZone(String license_plate) {
		List<Object[]> trips = tripRepository.getTripsVehicleZone(license_plate, StatusTrip.PENDIENTE.name());
		List<TripDto> tripDtos = new ArrayList<>();
		for (Object[] row : trips) {
		    TripDto tripDto = TripDto.builder()
		            .id((Integer) row[0])
		            .name("Viaje No " + (Integer) row[0] + " " + (String) row[2])
		            .status((String) row[1])
		            .build();
		    tripDtos.add(tripDto);
		}
		return tripDtos;
	}
	@Override
	public VehiclePackagesDto getpackages(String idDriver) {
		VehiclePackagesDto vehiclePackagesDto = new VehiclePackagesDto();
		List<Object[]> trips = tripRepository.getTripsActive(idDriver, StatusTrip.VIAJANDO.name());
		if(trips.size() == 0) {
			List<VehicleDriverDto> driver =vehicleRepository.findVehicleDriverByDriverId(idDriver);
			return VehiclePackagesDto.builder()
					.license_plate(driver.get(0).getLicense_plate())
					.name_trip("Viaje no: NA" )
					.id_trip(0)
					.packages(null)
					.build();
		}
		Object[] generalObject = trips.get(0);
		List<PackageToDeliverDto> packages = new ArrayList<>();
		for(Object[] row : trips) {			
			PackageToDeliverDto packageD = PackageToDeliverDto.builder()
					.id((Integer)row[2])
					.name("Paquete No " + row[2] + ": " + row[3])
					.build();
			packages.add(packageD);
		}
		 vehiclePackagesDto = VehiclePackagesDto.builder()
				.license_plate(generalObject[1].toString())
				.name_trip("Viaje no: " + generalObject[0] + " " + generalObject[4])
				.id_trip((Integer) generalObject[0])
				.packages(packages)
				.build();
		return vehiclePackagesDto;
	}
	@Override
	public Page<SummaryTrip> getAllTrips(int pageNumber, String id_driver) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5); 
		Page<Object[]> objectPage = (id_driver == null || id_driver.isEmpty()) ? tripRepository.getAllTrips(pageable): tripRepository.getAllTripsByIdDriver(pageable, id_driver);
		
		List<SummaryTrip> summaryTrips = new ArrayList<>();

		for (Object[] tripData : objectPage) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    String date = sdf.format(tripData[5]);
		    SummaryTrip summaryTrip = SummaryTrip.builder()
		    		.id_trip((Integer)tripData[0])
		    		.zone_name((String)tripData[1])
		    		.driver_name((String)tripData[2])
		    		.license_plate((String)tripData[3])
		    		.status_trip((String)tripData[4])
		    		.date_trip((String)date)
		    		.build();
		    summaryTrips.add(summaryTrip);
		}
		Page<SummaryTrip> summaryTripPage = new PageImpl<>(summaryTrips, objectPage.getPageable() , objectPage.getTotalElements());
		return summaryTripPage;
    }

}
