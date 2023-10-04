package com.ssafy.pcgg.domain.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SaveQuoteRequestDto {
	private Long cpuId;
	private Long mainboardId;
	private Long ssdId;
	private Long ramId;
	private Long gpuId;
	private Long chassisId;
	private Long powerId;
	private Long coolerId;
	private Long userId;
}
