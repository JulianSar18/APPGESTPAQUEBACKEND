package com.tc_28.api.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tc_28.api.dto.LiquidationDto;

public interface LiquidationService {
	public void callProcedureLiquidation();
	public Page<LiquidationDto> findAllLiquidations(int page);
	public List<LiquidationDto> findAllLiquidationsNoPaginated();
}
