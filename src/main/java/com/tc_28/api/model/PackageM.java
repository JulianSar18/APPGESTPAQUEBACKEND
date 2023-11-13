package com.tc_28.api.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "PACKAGES")
@Builder
public class PackageM {
	@Id
	@GeneratedValue(generator = "IdPackage")
    @SequenceGenerator(name = "IdPackage", sequenceName = "SEQ_PACKAGES", allocationSize = 1)
	@Column(name ="ID")
	private int id;
	@Column(name ="CATEGORY")
	private String category;
	@Column(name ="STATUS")
	private int status;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ZONE")
	private ZoneM zone;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TRIP")
	private TripM trip;

}
