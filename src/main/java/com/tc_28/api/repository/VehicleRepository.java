package com.tc_28.api.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tc_28.api.dto.VehicleDriverDto;
import com.tc_28.api.model.VehicleM;

@Repository
public class VehicleRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private final DataSource getDataSource() {
        return (this.jdbcTemplate != null ? this.jdbcTemplate.getDataSource() : null);
    }
	
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet result = null;
	
	public int addVehicle(VehicleM vehicle) {
		String INSERT_VEHICLE = "INSERT INTO VEHICLES(LICENSE_PLATE, ID_DRIVER) VALUES (?,?)";
		int out = 0; 
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(INSERT_VEHICLE);
			ps.setString(1, vehicle.getLicense_plate());
			ps.setString(2, vehicle.getId_driver());
			out = ps.executeUpdate();			
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return out;		
	}
	
	public List<VehicleDriverDto> findAllVehicleDriverNotEntered() {
		String SELECT_VEHICLE_DRIVER = "SELECT cv.NIT, cv.LICENSE_PLATE, d.NAME, d.IDENTIFICATION_CARD FROM VEHICLES v "
			    + "JOIN CONTRACTORS_VEHICLES cv ON v.LICENSE_PLATE = cv.LICENSE_PLATE "
			    + "JOIN DRIVERS d ON v.ID_DRIVER = d.IDENTIFICATION_CARD "
			    + "WHERE v.INCOME IS NULL OR v.INCOME = 0";				
		List<VehicleDriverDto> vehiclesDrivers = new ArrayList<>(); 
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(SELECT_VEHICLE_DRIVER);
			result = ps.executeQuery();	
			while(result.next()) {
				String nit = result.getString("NIT");
				String license_plate = result.getString("LICENSE_PLATE");
				String driver_name = result.getString("NAME");
				String identification_card = result.getString("IDENTIFICATION_CARD");
				VehicleDriverDto vehicleDriverDto = VehicleDriverDto.builder()
						.nit(nit)
						.license_plate(license_plate)
						.driver_name(driver_name)
						.identification_card(identification_card)
						.build();
				vehiclesDrivers.add(vehicleDriverDto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vehiclesDrivers;		
	}
	
	public List<VehicleDriverDto> findVehicleDriverByDriverId(String driver_id) {
		String SELECT_VEHICLE_DRIVER = "SELECT cv.NIT, cv.LICENSE_PLATE, d.NAME, d.IDENTIFICATION_CARD FROM VEHICLES v "
			    + "JOIN CONTRACTORS_VEHICLES cv ON v.LICENSE_PLATE = cv.LICENSE_PLATE "
			    + "JOIN DRIVERS d ON v.ID_DRIVER = d.IDENTIFICATION_CARD WHERE v.ID_DRIVER = ?";				
		List<VehicleDriverDto> vehiclesDrivers = new ArrayList<>(); 
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(SELECT_VEHICLE_DRIVER);
			ps.setString(1, driver_id);
			result = ps.executeQuery();	
			while(result.next()) {
				String nit = result.getString("NIT");
				String license_plate = result.getString("LICENSE_PLATE");
				String driver_name = result.getString("NAME");
				String identification_card = result.getString("IDENTIFICATION_CARD");
				VehicleDriverDto vehicleDriverDto = VehicleDriverDto.builder()
						.nit(nit)
						.license_plate(license_plate)
						.driver_name(driver_name)
						.identification_card(identification_card)
						.build();
				vehiclesDrivers.add(vehicleDriverDto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vehiclesDrivers;		
	}
	
	public VehicleM updateVehicleIncome(VehicleDriverDto vehicleDriver) {
		VehicleM vehicle = new VehicleM();
		Random random = new Random();
		int randomZone = random.nextInt(5) + 1;
		String UPDATE_VEHICLE = "UPDATE VEHICLES "
				+ "SET INCOME = 1, "
				+ "ID_ZONE = " + randomZone
				+ " WHERE LICENSE_PLATE = ?" ;	
		String SELECT_UPDATE_VEHICLE = "SELECT V.LICENSE_PLATE, v.INCOME, v.ID_ZONE, v.ID_DRIVER "
				+ "FROM VEHICLES v "
				+ "WHERE v.ID_DRIVER = " + vehicleDriver.getIdentification_card();
		try {			
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(UPDATE_VEHICLE);
			ps.setString(1, vehicleDriver.getLicense_plate());
			ps.executeUpdate();
			result = ps.executeQuery(SELECT_UPDATE_VEHICLE);
			while(result.next()) {
				String license_plate = result.getString("LICENSE_PLATE");
				int income = result.getInt("INCOME");
				int id_zone = result.getInt("ID_ZONE");
				String id_driver = result.getString("ID_DRIVER");
				vehicle = VehicleM.builder()
						.license_plate(license_plate)
						.income(income)
						.id_zone(id_zone)
						.id_driver(id_driver)
						.build();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vehicle;		
	}
	
	public Page<VehicleDriverDto> findAllVehicleDriverEntered(Pageable page) {		
		String SELECT_VEHICLE_DRIVER = "SELECT cv.NIT, cv.LICENSE_PLATE, d.NAME, d.IDENTIFICATION_CARD FROM VEHICLES v "
			    + "JOIN CONTRACTORS_VEHICLES cv ON v.LICENSE_PLATE = cv.LICENSE_PLATE "
			    + "JOIN DRIVERS d ON v.ID_DRIVER = d.IDENTIFICATION_CARD "
			    + "WHERE v.INCOME = 1 ";		
		String countSql = "SELECT COUNT(*) FROM (" + SELECT_VEHICLE_DRIVER + ")";
		int total = jdbcTemplate.queryForObject(countSql, Integer.class);
		int offset = page.getPageNumber() * page.getPageSize();
		int pageSize = page.getPageSize();
		String paginatedSql = SELECT_VEHICLE_DRIVER + " ORDER BY cv.NIT OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
		List<VehicleDriverDto> vehiclesDrivers = new ArrayList<>(); 
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(paginatedSql);
			ps.setInt(1, offset);
			ps.setInt(2, pageSize);
			result = ps.executeQuery();	
			while(result.next()) {
				String nit = result.getString("NIT");
				String license_plate = result.getString("LICENSE_PLATE");
				String driver_name = result.getString("NAME");
				String identification_card = result.getString("IDENTIFICATION_CARD");
				VehicleDriverDto vehicleDriverDto = VehicleDriverDto.builder()
						.nit(nit)
						.license_plate(license_plate)
						.driver_name(driver_name)
						.identification_card(identification_card)
						.build();
				vehiclesDrivers.add(vehicleDriverDto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new PageImpl<>(vehiclesDrivers, page, total);		
	}
	
	public VehicleM updateVehicleTraveling(VehicleDriverDto vehicleDriver, int status) {
		VehicleM vehicle = new VehicleM();
		String UPDATE_VEHICLE = "UPDATE VEHICLES "
				+ "SET INCOME = ? "
				+ "WHERE LICENSE_PLATE = ?" ;	
		String SELECT_UPDATE_VEHICLE = "SELECT V.LICENSE_PLATE, v.INCOME, v.ID_ZONE, v.ID_DRIVER "
				+ "FROM VEHICLES v "
				+ "WHERE v.ID_DRIVER = " + vehicleDriver.getIdentification_card();
		try {			
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(UPDATE_VEHICLE);
			ps.setInt(1, status);
			ps.setString(2, vehicleDriver.getLicense_plate());
			ps.executeUpdate();
			result = ps.executeQuery(SELECT_UPDATE_VEHICLE);
			while(result.next()) {
				String license_plate = result.getString("LICENSE_PLATE");
				int income = result.getInt("INCOME");
				int id_zone = result.getInt("ID_ZONE");
				String id_driver = result.getString("ID_DRIVER");
				vehicle = VehicleM.builder()
						.license_plate(license_plate)
						.income(income)
						.id_zone(id_zone)
						.id_driver(id_driver)
						.build();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vehicle;		
	}
	
	public Page<VehicleDriverDto> findVehicleDriverEnteredByIdDriver(Pageable page, String id_driver) {		
		String SELECT_VEHICLE_DRIVER = "SELECT cv.NIT, cv.LICENSE_PLATE, d.NAME, d.IDENTIFICATION_CARD FROM VEHICLES v "
			    + "JOIN CONTRACTORS_VEHICLES cv ON v.LICENSE_PLATE = cv.LICENSE_PLATE "
			    + "JOIN DRIVERS d ON v.ID_DRIVER = d.IDENTIFICATION_CARD "
			    + "WHERE v.INCOME = 1 AND d.IDENTIFICATION_CARD = ? ";		
		String countSql = "SELECT COUNT(*) FROM (" + SELECT_VEHICLE_DRIVER + ")";
		int total = jdbcTemplate.queryForObject(countSql, Integer.class, id_driver);
		int offset = page.getPageNumber() * page.getPageSize();
		int pageSize = page.getPageSize();
		String paginatedSql = SELECT_VEHICLE_DRIVER + " ORDER BY cv.NIT OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
		List<VehicleDriverDto> vehiclesDrivers = new ArrayList<>(); 
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(paginatedSql);
			ps.setString(1, id_driver);
			ps.setInt(2, offset);
			ps.setInt(3, pageSize);
			result = ps.executeQuery();	
			while(result.next()) {
				String nit = result.getString("NIT");
				String license_plate = result.getString("LICENSE_PLATE");
				String driver_name = result.getString("NAME");
				String identification_card = result.getString("IDENTIFICATION_CARD");
				VehicleDriverDto vehicleDriverDto = VehicleDriverDto.builder()
						.nit(nit)
						.license_plate(license_plate)
						.driver_name(driver_name)
						.identification_card(identification_card)
						.build();
				vehiclesDrivers.add(vehicleDriverDto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new PageImpl<>(vehiclesDrivers, page, total);		
	}
}
