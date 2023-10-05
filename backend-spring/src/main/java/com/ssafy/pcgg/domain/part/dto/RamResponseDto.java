package com.ssafy.pcgg.domain.part.dto;

import lombok.*;


@ToString
@Getter
@Setter
@NoArgsConstructor
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
