package com.ssafy.pcgg.domain.part.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@AllArgsConstructor
public class PowerResponseDto extends PartDto {
    private Long id;
    private Boolean extinct;
    private String size;
    private String grade;
    private Integer output;
    private BigDecimal depth;
    private Integer freeWarrantyPeriod;
    private Integer classColumn;
}
