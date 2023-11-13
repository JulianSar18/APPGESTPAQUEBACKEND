package com.tc_28.api.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tc_28.api.model.VehicleTripM;
@Repository
public class VehicleTripRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private final DataSource getDataSource() {
        return (this.jdbcTemplate != null ? this.jdbcTemplate.getDataSource() : null);
    }
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet result = null;
	
	public int addVehicleTrip(VehicleTripM vehicleTrip) {
		String INSERT_CONTRACTOR_VEHICLE = "INSERT INTO VEHICLES_TRIPS(LICENSE_PLATE, ID_TRIP) VALUES (?,?)";		
		int out = 0; 
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(INSERT_CONTRACTOR_VEHICLE);
			ps.setString(1, vehicleTrip.getLicense_plate());
			ps.setInt(2, vehicleTrip.getId_trip());
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
	
	public VehicleTripM licensePlateByIdTrip(int id_trip) {
		VehicleTripM vehicleTripM = new VehicleTripM();
		String SELECT_LICENSE_PLATE= "SELECT vt.LICENSE_PLATE, vt.ID_TRIP "
				+ "FROM VEHICLES_TRIPS vt "
			    + "WHERE vt.ID_TRIP = ?";		
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(SELECT_LICENSE_PLATE);
			ps.setInt(1, id_trip);
			result = ps.executeQuery();	
			while(result.next()) {
				String licensePlate = result.getString("LICENSE_PLATE");
				int idTrip = result.getInt("ID_TRIP");
				vehicleTripM.setLicense_plate(licensePlate);
				vehicleTripM.setId_trip(idTrip);
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
		return vehicleTripM;		
	}
	
}
