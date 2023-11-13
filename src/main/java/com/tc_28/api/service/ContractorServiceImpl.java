package com.tc_28.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tc_28.api.dto.ContractorDto;
import com.tc_28.api.model.ContractorM;
import com.tc_28.api.repository.ContractorRepository;


@Service
public class ContractorServiceImpl implements ContractorService{
	
	@Autowired
	private ContractorRepository contractorRepository;
	@Override
	public ContractorDto addContractor(ContractorDto contractor) {
		ContractorM contractorM = new ContractorM();
		contractorM.setNit(contractor.getNit());
		contractorM.setCompany_name(contractor.getCompany_name());
		contractorRepository.addContractor(contractorM);
		return contractor;
	}
	@Override
	public List<ContractorDto> getContractors() {		
		
		List<ContractorM> contractors = contractorRepository.findAllContractor();
		
		List<ContractorDto> contractorsDto = contractors.stream().map(x -> ContractorDto.builder()
				.nit(x.getNit())
				.company_name(x.getCompany_name())
				.build()).collect(Collectors.toList());
		
		
		return contractorsDto;
	}
	@Override
	public List<ContractorDto> getContractorsWithOuthCommission() {
		List<ContractorM> contractorsWithOutcommission = contractorRepository.findAllContractorWithOutCommission();
		List<ContractorDto> contractorsDto = contractorsWithOutcommission.stream().map(x -> ContractorDto.builder()
				.nit(x.getNit())
				.company_name(x.getCompany_name())
				.build()).collect(Collectors.toList());
		return contractorsDto;
	}
	@Override
	public ContractorDto getContractorById(String nit) {
		ContractorM contractor = contractorRepository.findContractorById(nit);
		ContractorDto contractorDto = ContractorDto.builder()
				.nit(contractor.getNit())
				.company_name(contractor.getCompany_name())
				.build();
		return contractorDto;
	}	
	
}
