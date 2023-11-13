package com.tc_28.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum StatusTrip {
	
    COMPLETADO("completado"),
	PENDIENTE("pendiente"),
	VIAJANDO("viajando");
	
	private final String displayName;
}
