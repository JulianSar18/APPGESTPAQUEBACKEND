package com.tc_28.api.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tc_28.api.dto.LiquidationDto;
import com.tc_28.api.service.LiquidationService;
import com.tc_28.api.util.OpenPdf;

@RestController
@CrossOrigin
@RequestMapping("/api/liquidation")
public class LiquidationController {
	@Autowired 
	private LiquidationService liquidationService;
	@GetMapping("/manual/ExecuteProcedure")
	public ResponseEntity<Object> executeProcedure(){
		try {
			liquidationService.callProcedureLiquidation();
			return new ResponseEntity<>("Procedimiento ejecutado", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>("El procedimiento no pudo ser ejecutado" + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAllLiquidations(@RequestParam(required = true) int page){
		Page<LiquidationDto> liquidations = liquidationService.findAllLiquidations(page);
		return new ResponseEntity<>(liquidations, HttpStatus.OK);
	}
	@GetMapping(value = "/pdf/liquidations", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> liquidationReports() throws IOException {
		List<LiquidationDto> liquidations = liquidationService.findAllLiquidationsNoPaginated();
		ByteArrayInputStream bis = OpenPdf.liquidationsReport(liquidations);
		HttpHeaders headers = new HttpHeaders();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	    String currentDateTime = dateFormat.format(new Date());
	    String fileName = "liquidation_" + currentDateTime + ".pdf";
		headers.add("Content-Disposition", "attachment;filename="+fileName);
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}
 
}
