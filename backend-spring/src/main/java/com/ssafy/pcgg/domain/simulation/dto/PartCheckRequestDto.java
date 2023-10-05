package com.ssafy.pcgg.domain.simulation.dto;

import com.ssafy.pcgg.domain.part.entity.*;
import lombok.*;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PartCheckRequestDto {
	private Integer cpuId;
	private Integer coolerId;
	private Integer mainboardId;
	private Integer ramId;
	private Integer gpuId;
	private Integer ssdId;
	private Integer caseId;
	private Integer powerId;
}


