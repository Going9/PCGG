package com.ssafy.pcgg.domain.part.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@ToString
@Getter
@AllArgsConstructor
public class RamResponseDto extends PartDto {
    private Long id;
    private Boolean extinct;
    private String memorySpec;
    private Integer memoryClock;
    private Boolean heatSink;
    private Integer classColumn;
    private Integer capacity;
}
