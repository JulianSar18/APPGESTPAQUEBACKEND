package com.tc_28.api.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="TRIPS")
public class TripM {
	@Id
	@GeneratedValue(generator = "IdTrip")
    @SequenceGenerator(name = "IdTrip", sequenceName = "SEQ_TRIP_ID", allocationSize = 1)
	@Column(name="ID")
	private int id;
	@Column(name="DATE_PACKAGE")
	private Date date;
	@Column(name = "STATUS")
	private String status;
	@Column(name="CAPACITY")
	private int capacity;
	@OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PackageM> packages;
}
