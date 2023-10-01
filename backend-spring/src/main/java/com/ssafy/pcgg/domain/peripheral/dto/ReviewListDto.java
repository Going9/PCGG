package com.ssafy.pcgg.domain.peripheral.dto;

import org.springframework.data.domain.Slice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewListDto {
	private String avgRating;
	private Slice<ReviewDto> reviewDtos;
}
