package com.ssafy.pcgg.domain.peripheral.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponseDto {
	Long ratingId;
	String avgRating;
}
