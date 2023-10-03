package com.ssafy.pcgg.domain.user.dto;

import com.ssafy.pcgg.domain.recommend.entity.QuoteEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class UserShareResponse {
    private Long id;
    private Long quoteId;
    private String title;
    private String content;
    private String summary;
    private LocalDateTime createdAt;
}
