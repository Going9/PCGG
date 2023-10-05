package com.ssafy.pcgg.domain.simulation.dto;

import lombok.*;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PartCheckResponseDto {
	private String partType1;
	private String partType2;
	private String detail;
}


