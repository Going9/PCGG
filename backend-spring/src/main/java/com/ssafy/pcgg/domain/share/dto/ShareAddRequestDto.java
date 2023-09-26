package com.ssafy.pcgg.domain.share.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShareAddRequestDto {
	private String title;
	private String content;
	private String imageSource;
	private String summary;
	private ShareAddQuoteRequestDto shareAddQuoteRequestDto;
}
