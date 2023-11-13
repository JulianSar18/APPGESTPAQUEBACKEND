package com.tc_28.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleM {
	private String license_plate;
	private int income;
	private int id_zone;
	private String id_driver;
}
