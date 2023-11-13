package com.tc_28.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SummaryTrip {
	private int id_trip;
	private String zone_name;
	private String driver_name;
	private String license_plate;
	private String status_trip;
	private String date_trip;
}
