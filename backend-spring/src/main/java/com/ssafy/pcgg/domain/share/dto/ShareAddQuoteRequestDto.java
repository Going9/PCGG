package com.ssafy.pcgg.domain.share.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShareAddQuoteRequestDto {
	@Schema(description = "cpuId" , example = "1")
	private Long cpuId;

	@Schema(description = "mainboardId" , example = "1")
	private Long mainboardId;

	@Schema(description = "ssdId" , example = "1")
	private Long ssdId;

	@Schema(description = "ramId" , example = "1")
	private Long ramId;

	@Schema(description = "gpuId" , example = "1")
	private Long gpuId;

	@Schema(description = "chassisId" , example = "1")
	private Long chassisId;

	@Schema(description = "powerId" , example = "1")
	private Long powerId;

	@Schema(description = "coolerId" , example = "1")
	private Long coolerId;
}
