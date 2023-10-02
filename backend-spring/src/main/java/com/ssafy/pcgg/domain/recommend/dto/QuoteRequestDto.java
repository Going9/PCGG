package com.ssafy.pcgg.domain.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuoteRequestDto {
	private String usage;
	private int budget;
	private int priority;
	private String caseSize;
	private boolean as;
	private int ssdSize;
}
