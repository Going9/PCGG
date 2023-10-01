package com.ssafy.pcgg.domain.part.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@AllArgsConstructor
public class GpuResponseDto extends PartDto {
    private Long id;
    private Boolean extinct;
    private Integer neededPower;
    private BigDecimal width;
    private BigDecimal thickness;
    private Integer score;
    private Integer classColumn;

}
