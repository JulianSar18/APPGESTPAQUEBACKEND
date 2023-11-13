package com.tc_28.api.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "ZONES")
public class ZoneM {
	@Id
	@Column(name="ID")
	private int id;
	@Column(name = "ZONE_NAME")
	private String zone_name;
	@OneToMany(mappedBy = "zone")
	@JsonIgnore
	private List<PackageM> packages;
}
