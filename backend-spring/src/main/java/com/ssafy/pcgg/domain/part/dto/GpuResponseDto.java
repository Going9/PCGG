package com.ssafy.pcgg.domain.part.dto;

import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
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
