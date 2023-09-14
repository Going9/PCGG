package com.ssafy.pcgg.domain.peripheral.entity;

import jakarta.persistence.*;
import jakarta.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "peripheral_printer")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Printer {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 50, unique = true, nullable = false)
	private String name;

	@Nullable
	@Column(name = "lprice")
	private Integer lprice;

	@Nullable
	@Column(name = "hprice")
	private Integer hprice;

	@Column(name = "image_source", length = 100, unique = true, nullable = false)
	private String imageSource;

	@Column(name = "link", length = 100, nullable = false)
	private String link;

	@Column(name = "brand", length = 50, nullable = false)
	private String brand;

	@Column(name = "extinct", columnDefinition = "TINYINT(1)", nullable = false)
	private Boolean extinct;

}
