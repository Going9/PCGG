package com.ssafy.pcgg.domain.share.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
	private Long id;
	private Long shareId;
	private Long userId;
	// private String userNickname;	//추후 UserEntity에 nickname 추가시, 수정예정
	private String content;
}
