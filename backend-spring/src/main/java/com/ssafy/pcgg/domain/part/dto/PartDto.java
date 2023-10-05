package com.ssafy.pcgg.domain.part.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PartDto {
    //부품들의 공통속성을 가지는 클래스
    String name;
    Integer price;
    String imageSource;
    Boolean extinct;
}
