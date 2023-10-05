package com.ssafy.pcgg.domain.part.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "part_ssd")
public class SsdEntity {
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

    // @Getter
    @Column(name="pcie_ver")
    private Integer pcieVer;

    @Column(name="reading_speed")
    private Integer readingSpeed;

    @Column(name="writing_speed")
    private Integer writingSpeed;

    @Column(length = 20)
    private String manufacturer;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private BigDecimal capacity;
}
