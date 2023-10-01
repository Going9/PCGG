package com.ssafy.pcgg.domain.part.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
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
