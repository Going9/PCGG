package com.ssafy.pcgg.domain.part.dto;

import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
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
