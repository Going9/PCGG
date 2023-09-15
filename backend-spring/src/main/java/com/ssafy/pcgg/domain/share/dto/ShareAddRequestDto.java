package com.ssafy.pcgg.domain.share.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShareAddRequestDto {
	private Long userId;
	private String title;
	private String content;
	private String imageSource;
	private String summary;
	private ShardAddQuoteRequestDto shardAddQuoteRequestDto;
}
