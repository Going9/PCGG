package com.ssafy.pcgg.domain.part.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CpuResponseDto extends PartDto {
	Long id;
	String socketInfo;
	boolean DDR4;
	boolean DDR5;
	boolean integratedGraphics;
	boolean coolerIncluded;
	Integer singleScore;
	Integer multiScore;
	int classNumber;
}
