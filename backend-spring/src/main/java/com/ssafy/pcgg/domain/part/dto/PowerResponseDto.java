package com.ssafy.pcgg.domain.part.dto;

import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
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
