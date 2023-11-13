package com.tc_28.api.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tc_28.api.model.ContractorVehicleM;

@Repository
public class ContractorVehicleRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private final DataSource getDataSource() {
        return (this.jdbcTemplate != null ? this.jdbcTemplate.getDataSource() : null);
    }
	private Connection conn = null;
	private PreparedStatement ps = null;
	
	public int addContractorVehicle(ContractorVehicleM contractorVehicle) {
		String INSERT_CONTRACTOR_VEHICLE = "INSERT INTO CONTRACTORS_VEHICLES(NIT, LICENSE_PLATE) VALUES (?,?)";		
		int out = 0; 
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(INSERT_CONTRACTOR_VEHICLE);
			ps.setString(1, contractorVehicle.getNit());
			ps.setString(2, contractorVehicle.getLicense_plate());
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
}
