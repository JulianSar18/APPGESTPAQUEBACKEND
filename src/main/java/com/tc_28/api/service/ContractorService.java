package com.tc_28.api.service;

import java.util.List;

import com.tc_28.api.dto.ContractorDto;

public interface ContractorService {
 public ContractorDto addContractor(ContractorDto contractor);
 public List<ContractorDto> getContractors();
 public List<ContractorDto> getContractorsWithOuthCommission(); 
 public ContractorDto getContractorById(String nit);
}
