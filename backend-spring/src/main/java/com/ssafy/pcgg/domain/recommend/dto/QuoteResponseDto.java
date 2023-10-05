package com.ssafy.pcgg.domain.recommend.dto;

import com.ssafy.pcgg.domain.part.entity.*;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@ToString
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
