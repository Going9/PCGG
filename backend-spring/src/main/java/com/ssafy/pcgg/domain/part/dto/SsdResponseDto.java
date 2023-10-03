package com.ssafy.pcgg.domain.part.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@AllArgsConstructor
public class SsdResponseDto extends PartDto {
    private Long id;
    private Boolean extinct;
    private Integer pcieVer;
    private Integer readingSpeed;
    private Integer writingSpeed;
    private String manufacturer;
    private BigDecimal capacity;
}
