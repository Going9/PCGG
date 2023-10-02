package com.ssafy.pcgg.domain.share.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShareAddRequestDto {

	@Schema(description = "제목", example = "제목")
	private String title;

	@Schema(description = "내용", example = "내용입니다.")
	private String content;

	@Schema(description = "한줄설명", example = "한줄설명입니다.")
	private String summary;

	private ShareAddQuoteRequestDto shareAddQuoteRequestDto;

}
