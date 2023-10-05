package com.ssafy.pcgg.domain.part.dto;

import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoolerResponseDto extends PartDto {
    private Long id;
    private Boolean extinct;
    private String form;
    private String type;
    private Integer freeWarrantyPeriod;
    private BigDecimal height;
    private Integer fanCount;
    private BigDecimal maxFanNoise;
}
