package com.ssafy.pcgg.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserPeripheralResponse {
    private Long id;
    private String name;
    private Integer lprice;
    private Integer hprice;
    private String imageSource;
    private String link;
    private String brand;
    private Boolean extinct;
}
