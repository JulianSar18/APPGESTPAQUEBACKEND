package com.tc_28.api.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tc_28.api.dto.LiquidationDto;
import com.tc_28.api.model.StatusTrip;
@Repository
public class LiquidationRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	private final DataSource getDataSource() {
		return (this.jdbcTemplate != null ? this.jdbcTemplate.getDataSource() : null);
	}

	private Connection conn = null;
	private PreparedStatement ps = null;
	private CallableStatement st = null;
	private ResultSet result = null;

	public void executeCallProcedureInsertLiquidation() {
		try {
			conn = getDataSource().getConnection();
			String call = "{call CALCULATE_PAYMENT_AND_INSERT(?)}";
			st = conn.prepareCall(call);
			st.setString(1, StatusTrip.COMPLETADO.name());
			st.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Page<LiquidationDto> findAllLiquidations(Pageable page) {
		String SELECT_LIQUIDATIONS = "SELECT c.COMPANY_NAME, l.TRIPS, l.DELIVERIES, l.DATE_LIQUIDATION, l.TOTAL_VALUE "
				+ "FROM LIQUIDATIONS l "
				+ "JOIN CONTRACTORS c ON c.id_commission = l.id_commission ";
		String countSql = "SELECT COUNT(*) FROM (" + SELECT_LIQUIDATIONS + ")";
		int total = jdbcTemplate.queryForObject(countSql, Integer.class);
		int offset = page.getPageNumber() * page.getPageSize();
		int pageSize = page.getPageSize();
		String paginatedSql = SELECT_LIQUIDATIONS + " ORDER BY l.DATE_LIQUIDATION OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
		List<LiquidationDto> liquidations = new ArrayList<>(); 
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(paginatedSql);
			ps.setInt(1, offset);
			ps.setInt(2, pageSize);
			result = ps.executeQuery();	
			while(result.next()) {
				String company_name = result.getString("COMPANY_NAME");
				int number_trips = result.getInt("TRIPS");
				int number_deliveries = result.getInt("DELIVERIES");
				Date date = result.getDate("DATE_LIQUIDATION");
				int total_value = result.getInt("TOTAL_VALUE");				
				LiquidationDto liquidationDto = LiquidationDto.builder()
						.name_company(company_name)
						.number_trips(number_trips)
						.number_deliveries(number_deliveries)
						.date(date.toString())
						.total_value(total_value)
						.build();
				liquidations.add(liquidationDto);
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
		return new PageImpl<>(liquidations, page, total);		
	}
	
	public List<LiquidationDto> findAllLiquidationsNoPaginate() {
		String SELECT_LIQUIDATIONS = "SELECT c.COMPANY_NAME, l.TRIPS, l.DELIVERIES, l.DATE_LIQUIDATION, l.TOTAL_VALUE "
				+ "FROM LIQUIDATIONS l "
				+ "JOIN CONTRACTORS c ON c.id_commission = l.id_commission ";		
		List<LiquidationDto> liquidations = new ArrayList<>(); 
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(SELECT_LIQUIDATIONS);
			result = ps.executeQuery();	
			while(result.next()) {
				String company_name = result.getString("COMPANY_NAME");
				int number_trips = result.getInt("TRIPS");
				int number_deliveries = result.getInt("DELIVERIES");
				Date date = result.getDate("DATE_LIQUIDATION");
				int total_value = result.getInt("TOTAL_VALUE");				
				LiquidationDto liquidationDto = LiquidationDto.builder()
						.name_company(company_name)
						.number_trips(number_trips)
						.number_deliveries(number_deliveries)
						.date(date.toString())
						.total_value(total_value)
						.build();
				liquidations.add(liquidationDto);
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
		return liquidations;		
	}
}
