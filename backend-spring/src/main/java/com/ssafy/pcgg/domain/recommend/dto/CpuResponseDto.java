package com.ssafy.pcgg.domain.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CpuResponseDto extends PartDto {
	Long id;
	String socket_info;
	boolean DDR4;
	boolean DDR5;
	boolean integrated_graphics;
	boolean cooler_included;
	Integer single_score;
	Integer multi_score;
	int classNumber;
}
