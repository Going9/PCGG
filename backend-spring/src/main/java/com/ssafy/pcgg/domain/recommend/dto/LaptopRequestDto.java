package com.ssafy.pcgg.domain.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class LaptopRequestDto {
    private String usage;
    private int budget;
    private boolean os;
    private int priority;
    private boolean as;
}
