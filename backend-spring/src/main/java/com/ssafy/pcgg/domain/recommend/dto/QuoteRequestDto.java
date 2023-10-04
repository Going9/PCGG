package com.ssafy.pcgg.domain.recommend.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuoteRequestDto {
	private String usage;
	private Integer budget;
	private Integer priority;
	private String caseSize;
	private Boolean as;
	private Integer ssdSize;
}
