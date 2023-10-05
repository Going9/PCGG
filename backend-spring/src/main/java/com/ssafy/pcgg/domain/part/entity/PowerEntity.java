package com.ssafy.pcgg.domain.part.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "part_power")
public class PowerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    // @Getter
    private Integer price;

    @Column(name="image_source", length=200)
    private String imageSource;

    private Boolean extinct;

    @Column(length = 10)
    private String size;

    // @Getter
    @Column(length = 20)
    private String grade;

    private Integer output;

    @Column(precision = 7, scale = 2)
    private BigDecimal depth;

    @Column(name = "free_warranty_period")
    private Integer freeWarrantyPeriod;

    @Setter
    @Column(name = "`class`", columnDefinition = "tinyint")
    private Integer classColumn;
}
