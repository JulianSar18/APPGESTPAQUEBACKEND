package com.tc_28.api.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tc_28.api.dto.LiquidationDto;
import com.tc_28.api.repository.LiquidationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LiquidationServiceImpl implements LiquidationService{
	@Autowired
	private LiquidationRepository liquidationRepository;
	private static final String TIME_ZONE = 
			  "America/Bogota";
	@Scheduled(cron = "0 0 0 1 * *", zone = TIME_ZONE)
	@Override
	public void callProcedureLiquidation() {
		ZoneId timeZone = ZoneId.of(TIME_ZONE);
        ZonedDateTime currentHour = ZonedDateTime.now(timeZone);
		log.info(currentHour + " " + "Inicia ejecucion callProcedureLiquidation" );		
		liquidationRepository.executeCallProcedureInsertLiquidation();		
		log.info(currentHour + " " + "Termina proceso");
	}
	@Override
	public Page<LiquidationDto> findAllLiquidations(int page) {
		Pageable pageable = PageRequest.of(page - 1, 5); 
		return liquidationRepository.findAllLiquidations(pageable);
	}
	@Override
	public List<LiquidationDto> findAllLiquidationsNoPaginated() {
		return liquidationRepository.findAllLiquidationsNoPaginate();
	}

}
