package com.ssafy.pcgg.domain.peripheral.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewRequestDto {
	private Long peripheralId;
	private Integer rating;
	private String review;
}
