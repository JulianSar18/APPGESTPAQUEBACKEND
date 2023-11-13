package com.tc_28.api.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tc_28.api.model.ContractorM;



@Repository
public class ContractorRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private final DataSource getDataSource() {
        return (this.jdbcTemplate != null ? this.jdbcTemplate.getDataSource() : null);
    }
	
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet result = null;
	public int addContractor(ContractorM contractor) {
		String INSERT_CONTRACTOR = "INSERT INTO CONTRACTORS(NIT, COMPANY_NAME) VALUES (?,?)";
		int out = 0; 
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(INSERT_CONTRACTOR);
			ps.setString(1, contractor.getNit());
			ps.setString(2, contractor.getCompany_name());
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
	
	public int addCommissionContractor(ContractorM contractor) {
		String UPDATE_CONTRACTOR = "UPDATE CONTRACTORS SET ID_COMMISSION = ? WHERE NIT = ?";
		int out = 0; 
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(UPDATE_CONTRACTOR);
			ps.setInt(1, contractor.getId_commission());
			ps.setString(2, contractor.getNit());			
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
	
	public List<ContractorM> findAllContractor() {
		String SELECT_CONTRACTORS = "SELECT c.NIT, c.COMPANY_NAME FROM CONTRACTORS c";
		List<ContractorM> contractors = new ArrayList<>(); 
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(SELECT_CONTRACTORS);
			result = ps.executeQuery();	
			while(result.next()) {
				String nit = result.getString("NIT");
				String company_name = result.getString("COMPANY_NAME");
				ContractorM contractor = ContractorM.builder()
							.nit(nit)
							.company_name(company_name)
							.build();
				contractors.add(contractor);				
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
		return contractors;		
	}
	
	public List<ContractorM> findAllContractorWithOutCommission() {
		String SELECT_CONTRACTORS = "SELECT c.NIT, c.COMPANY_NAME FROM CONTRACTORS c WHERE c.ID_COMMISSION IS NULL";
		List<ContractorM> contractors = new ArrayList<>(); 
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(SELECT_CONTRACTORS);
			result = ps.executeQuery();	
			while(result.next()) {
				String nit = result.getString("NIT");
				String company_name = result.getString("COMPANY_NAME");
				ContractorM contractor = ContractorM.builder()
							.nit(nit)
							.company_name(company_name)
							.build();
				contractors.add(contractor);				
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
		return contractors;		
	}
	public ContractorM findContractorById(String nit) {
		String SELECT_CONTRACTOR = "SELECT c.NIT, c.COMPANY_NAME FROM CONTRACTORS c WHERE c.NIT = ?";
		ContractorM contractor = new ContractorM();
		try {
			conn = getDataSource().getConnection();			
			ps = conn.prepareStatement(SELECT_CONTRACTOR);
			ps.setString(1, nit);	
			result = ps.executeQuery();	
			while(result.next()) {
				String nit1 = result.getString("NIT");
				String company_name = result.getString("COMPANY_NAME");
				contractor = ContractorM.builder()
							.nit(nit1)
							.company_name(company_name)
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
		return contractor;
	}
}
