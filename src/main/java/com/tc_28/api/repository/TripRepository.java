package com.tc_28.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tc_28.api.model.TripM;

import jakarta.transaction.Transactional;


public interface TripRepository extends JpaRepository<TripM, Integer>{
	@Query("SELECT DISTINCT t FROM TripM t LEFT JOIN FETCH t.packages p WHERE t.status = ?1 ")
	List<TripM> checkTrip(String status);
	@Query(value = "SELECT t.ID, t.STATUS, z.ZONE_NAME "
			+ "FROM TRIPS t "
			+ "JOIN PACKAGES p ON t.ID = p.ID_TRIP "
			+ "JOIN ZONES z	ON p.ID_ZONE = z.ID "
			+ "WHERE p.ID_ZONE = (SELECT v.ID_ZONE FROM VEHICLES v WHERE v.LICENSE_PLATE = ?1) "
			+ "AND t.STATUS = ?2 "
			+ "GROUP BY t.ID, t.STATUS, z.ZONE_NAME", nativeQuery = true)
	List<Object[]> getTripsVehicleZone(String license_plate, String status);
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Transactional
	@Query("UPDATE TripM t SET t.status = ?1 WHERE ID = ?2")
	public void updateStateTrip(String newStatus, int id);
	@Query(value ="SELECT t.ID, vt.LICENSE_PLATE, p.ID, p.CATEGORY, z.ZONE_NAME "
			+ "FROM TRIPS t "
			+ "JOIN PACKAGES p ON t.ID = p.ID_TRIP "
			+ "JOIN ZONES z on p.ID_ZONE = z.ID "
			+ "JOIN VEHICLES_TRIPS vt on vt.ID_TRIP = t.ID "
			+ "WHERE vt.LICENSE_PLATE = (SELECT v.LICENSE_PLATE FROM VEHICLES v WHERE v.id_driver = ?1) "
			+ "AND t.STATUS = ?2 AND p.STATUS = 0 ", nativeQuery = true)
	public List<Object[]> getTripsActive(String license_plate, String status);
	@Query(value="SELECT COUNT(*) "
			+ "FROM TRIPS t "
			+ "JOIN PACKAGES p ON t.ID = p.ID_TRIP "
			+ "WHERE p.STATUS = 0 "
			+ "AND t.ID = ?1 ", nativeQuery = true)
	public int checkPendingPackages(int id_trip);
	@Query(value="SELECT t.id, z.zone_name, d.name, v.license_plate, t.status, t.date_package "
			+ "FROM trips t "
			+ "JOIN packages p on p.id_trip = t.id "
			+ "JOIN vehicles_trips vt on vt.id_trip = t.id "
			+ "JOIN vehicles v on v.license_plate = vt.license_plate "
			+ "JOIN drivers d on d.identification_card = v.id_driver "
			+ "JOIN zones z on z.id = p.id_zone "
			+ "GROUP BY t.id, t.date_package, z.zone_name, d.name, v.license_plate, t.status "
			+ "ORDER BY t.id", 
			countQuery = "SELECT COUNT(DISTINCT t.id) AS total_rows "
					+ "FROM trips t "
					+ "JOIN packages p ON p.id_trip = t.id "
					+ "JOIN vehicles_trips vt ON vt.id_trip = t.id "
					+ "JOIN vehicles v ON v.license_plate = vt.license_plate "
					+ "JOIN drivers d ON d.identification_card = v.id_driver "
					+ "JOIN zones z ON z.id = p.id_zone", nativeQuery = true)
	public Page<Object[]> getAllTrips(Pageable pageable);
	@Query(value="SELECT t.id, z.zone_name, d.name, v.license_plate, t.status, t.date_package "
			+ "FROM trips t "
			+ "JOIN packages p on p.id_trip = t.id "
			+ "JOIN vehicles_trips vt on vt.id_trip = t.id "
			+ "JOIN vehicles v on v.license_plate = vt.license_plate "
			+ "JOIN drivers d on d.identification_card = v.id_driver "
			+ "JOIN zones z on z.id = p.id_zone "
			+ "WHERE d.identification_card = :idDriver "
			+ "GROUP BY t.id, t.date_package, z.zone_name, d.name, v.license_plate, t.status "
			+ "ORDER BY t.id", 
			countQuery = "SELECT COUNT(DISTINCT t.id) AS total_rows "
					+ "FROM trips t "
					+ "JOIN packages p ON p.id_trip = t.id "
					+ "JOIN vehicles_trips vt ON vt.id_trip = t.id "
					+ "JOIN vehicles v ON v.license_plate = vt.license_plate "
					+ "JOIN drivers d ON d.identification_card = v.id_driver "
					+ "JOIN zones z ON z.id = p.id_zone "
					+ "WHERE d.identification_card = :idDriver ", nativeQuery = true)
	public Page<Object[]> getAllTripsByIdDriver(Pageable pageable, @Param("idDriver") String id_driver);
	
}
