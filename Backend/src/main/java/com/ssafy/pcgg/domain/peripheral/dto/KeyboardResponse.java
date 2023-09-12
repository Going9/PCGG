package com.ssafy.pcgg.domain.peripheral.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeyboardResponse {

	private Long id;
	private String name;
	private Integer lprice;
	private Integer hprice;
	private String imageSource;
	private String link;
	private String brand;

}
