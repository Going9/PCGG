package com.ssafy.pcgg.domain.peripheral.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponseDto {
	Long ratingId;
	String avgRating;
}
