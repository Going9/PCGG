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
	private Integer quoteId;
	private String title;
	private String content;
	private String summary;
	private LocalDateTime createdAt;
	// private Integer mark;	// 추후 jwt 적용시 추가할 컬럼
}
