package com.tc_28.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tc_28.api.dto.CommissionDto;
import com.tc_28.api.model.CommissionM;
import com.tc_28.api.model.ContractorM;
import com.tc_28.api.repository.CommissionRepository;
import com.tc_28.api.repository.ContractorRepository;

@Service
public class CommissionServiceImpl implements CommissionService{
	@Autowired
	private CommissionRepository commissionRepository;	
	@Autowired
	private ContractorRepository contractorRepository;
	@Override
	public CommissionDto createCommission(CommissionDto commission) {
		CommissionM commissionM = CommissionM.builder()
				.per_delivery_commission(commission.getPer_delivery_commission())
				.per_trip_commission(commission.getPer_trip_commission())
				.build();		
		CommissionM commissionSaved = commissionRepository.save(commissionM);
		
		ContractorM contractorM = ContractorM.builder()
				.nit(commission.getNit())
				.id_commission(commissionSaved.getId())
				.build();
		contractorRepository.addCommissionContractor(contractorM);
		return commission;
	}

}
