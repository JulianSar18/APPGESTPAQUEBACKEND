package com.tc_28.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommissionDto {
	private String nit;
	private float per_trip_commission;
	private float per_delivery_commission;
}
