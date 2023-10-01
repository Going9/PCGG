package com.ssafy.pcgg.domain.part.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@AllArgsConstructor
public class ChassisResponseDto extends PartDto {
    private Long id;
    private Boolean extinct;
    private Boolean extendedAtx;
    private Boolean standardAtx;
    private Boolean microAtx;
    private Boolean miniItx;
    private BigDecimal width;
    private BigDecimal depth;
    private BigDecimal maxPowerDepth;
    private BigDecimal maxGpuDepth;
    private BigDecimal maxCoolerDepth;
}
