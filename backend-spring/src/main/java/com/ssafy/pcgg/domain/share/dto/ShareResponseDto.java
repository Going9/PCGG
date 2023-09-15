package com.ssafy.pcgg.domain.share.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShareResponseDto {
	private Long id;
	private Long userId;
	private Long quoteId;
	private String title;
	private String content;
	private LocalDateTime createdAt;
}
