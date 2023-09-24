package com.ssafy.pcgg.domain.peripheral.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
	private Long reviewId;
	private Long userId;
	private String userNickname;
	private Integer rating;
	private String review;
	private LocalDateTime createdAt;
}
