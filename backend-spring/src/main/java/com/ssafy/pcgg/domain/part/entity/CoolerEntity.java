package com.ssafy.pcgg.domain.part.entity;

import jakarta.persistence.*;
import lombok.Getter;


import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "part_cooler")
public class CoolerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    private Integer price;

    @Column(name="image_source", length=200)
    private String imageSource;

    private Boolean extinct;

    @Column(length = 20)
    private String form;

    @Column(length = 20)
    private String type;

    @Column(name = "free_warranty_period")
    private Integer freeWarrantyPeriod;

    @Column(precision = 7,scale = 2)
    private BigDecimal height;

    @Column(name = "fan_count")
    private Integer fanCount;

    @Column(name = "max_fan_noise", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal maxFanNoise;
}
