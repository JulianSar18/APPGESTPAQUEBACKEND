package com.tc_28.api.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehiclePackagesDto {
	private String license_plate;
	private String name_trip;
	private int id_trip;
	private List<PackageToDeliverDto> packages;
}
