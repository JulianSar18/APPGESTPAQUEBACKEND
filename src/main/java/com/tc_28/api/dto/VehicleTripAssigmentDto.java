package com.tc_28.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTripAssigmentDto {
	private int id_trip;
	private String nit;
	private String license_plate;
	private String driver_name;
	private String identification_card;
}
