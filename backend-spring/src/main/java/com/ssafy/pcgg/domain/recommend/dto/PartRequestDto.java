package com.ssafy.pcgg.domain.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PartRequestDto {
    private String usage;
    private int budget;
    private int priority;
    private boolean as;
}
