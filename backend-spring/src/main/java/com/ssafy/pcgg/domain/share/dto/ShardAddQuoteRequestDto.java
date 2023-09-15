package com.ssafy.pcgg.domain.share.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShardAddQuoteRequestDto {
	private Integer cpuId;
	private Integer mainboardId;
	private Integer ssdId;
	private Integer ramId;
	private Integer gpuId;
	private Integer chassisId;
	private Integer powerId;
	private Integer coolerId;
}
