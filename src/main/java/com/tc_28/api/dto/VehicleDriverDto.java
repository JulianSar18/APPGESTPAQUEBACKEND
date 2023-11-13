package com.tc_28.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDriverDto {
	private String nit;
	private String license_plate;
	private String driver_name;
	private String identification_card;
}
