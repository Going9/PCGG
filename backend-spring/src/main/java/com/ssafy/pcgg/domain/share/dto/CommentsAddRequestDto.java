package com.ssafy.pcgg.domain.share.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentsAddRequestDto {
	private Long userId;
	private String content;
}
