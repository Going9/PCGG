package com.ssafy.pcgg.domain.share.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShareAddQuoteRequestDto {
	private Long cpuId;
	private Long mainboardId;
	private Long ssdId;
	private Long ramId;
	private Long gpuId;
	private Long chassisId;
	private Long powerId;
	private Long coolerId;
}
