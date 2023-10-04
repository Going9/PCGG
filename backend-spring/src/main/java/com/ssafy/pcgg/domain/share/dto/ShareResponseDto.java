package com.ssafy.pcgg.domain.share.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class ShareResponseDto {
	private Long id;
	private Long userId;
	private String userNickname;
	private Long quoteId;
	private String title;
	private String content;
	private String summary;
	private LocalDateTime createdAt;
	private Long likeCnt;
	private Long dislikeCnt;
	private Long reviewCnt;
}
