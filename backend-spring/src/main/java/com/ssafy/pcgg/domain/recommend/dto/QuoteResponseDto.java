package com.ssafy.pcgg.domain.recommend.dto;

import com.ssafy.pcgg.domain.recommend.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class QuoteResponseDto {
	private CpuEntity cpu;
	private MainboardEntity mainboard;
	private SsdEntity ssd;
	private RamEntity ram;
	private GpuEntity gpu;
	private ChassisEntity chassis;
	private PowerEntity power;
	private CoolerEntity cooler;
	private Integer benchScore;
	private Integer totalPrice;
}
