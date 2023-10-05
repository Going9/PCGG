package com.ssafy.pcgg.domain.recommend.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LaptopResponseDto {
    private Long id;
    private String type;
    private String os;
    private Integer screenSize;
    private Integer refreshRate;
    private String resolution;
    private Integer brightness;
    private String cpu;
    private Integer ramCapacity;
    private Boolean ramUpgradeable;
    private String gpu;
    private Integer tgp;
    private BigDecimal ssd;
    private Boolean cellular;
    private Boolean hdmi;
    private Boolean thunderbolt;
    private Integer usbA;
    private Integer usbC;
    private String sdCard;
    private BigDecimal weight;
    private BigDecimal batter;
    private String name;
    private Integer price;
    private String imageSource;
    private Boolean extinct;
    private String manufacturer;
}
