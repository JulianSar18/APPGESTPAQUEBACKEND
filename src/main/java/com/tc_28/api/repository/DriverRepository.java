package com.tc_28.api.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tc_28.api.model.DriverM;

@Repository
public class DriverRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private final DataSource getDataSource() {
        return (this.jdbcTemplate != null ? this.jdbcTemplate.getDataSource() : null);
    }
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet result = null;
	
	
	public int addDriver(DriverM driver) {
		String INSERT_DRIVER = "INSERT INTO DRIVERS(IDENTIFICATION_CARD, NAME) VALUES (?,?)";
		int out = 0; 
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(INSERT_DRIVER);
			ps.setString(1, driver.getIdentification_card());
			ps.setString(2, driver.getName());
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
	
	public DriverM getDriverById(String identification) {
		String SELECT_DRIVER = "SELECT d.IDENTIFICATION_CARD, d.NAME FROM DRIVERS d WHERE d.IDENTIFICATION_CARD = ?";
		DriverM driver = new DriverM();
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(SELECT_DRIVER);
			ps.setString(1, identification);
			result = ps.executeQuery();
			while(result.next()) {
				String id = result.getString("IDENTIFICATION_CARD");
				String name = result.getString("NAME");
				driver = DriverM.builder()
						.identification_card(id)
						.name(name)
						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return driver;
	}
}
