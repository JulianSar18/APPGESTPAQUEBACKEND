package com.tc_28.api.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LiquidationM {
	private int id;
	private int number_trips;
	private int number_deliveries;
	private Date date_liquidation;
	private int id_commission;
	private float total_value;
}
