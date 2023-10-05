package com.ssafy.pcgg.domain.part.dto;

import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
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
