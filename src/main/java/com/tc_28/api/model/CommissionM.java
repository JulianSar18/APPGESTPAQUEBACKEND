package com.tc_28.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "COMMISSIONS")
public class CommissionM {
	@Id
	@GeneratedValue(generator = "IdCommission")
    @SequenceGenerator(name = "IdCommission", sequenceName = "SEQ_COMMISSIONS", allocationSize = 1)
	@Column(name="ID")
	private int id;
	@Column(name="PER_TRIP_COMMISSION")
	private float per_trip_commission;
	@Column(name="PER_DELIVERY_COMMISSION")
	private float per_delivery_commission;
}
