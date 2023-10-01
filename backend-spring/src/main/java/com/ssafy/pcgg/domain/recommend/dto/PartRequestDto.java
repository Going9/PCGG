package com.ssafy.pcgg.domain.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class PartRequestDto {
    private String category;
    private String usage;
    private int budget;
    private int priority;
    private boolean as;
}
