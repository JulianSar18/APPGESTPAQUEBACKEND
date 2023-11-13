package com.tc_28.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LiquidationDto {
	private String name_company;
	private int number_trips;
	private int number_deliveries;
	private String date;
	private int total_value;
}
