package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "part_cpu")
public class CpuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Getter
    private Integer price;

    @Column(name="image_source", length=200)
    private String imageSource;

    private Boolean extinct;

    @Getter
    @Column(name="socket_info", length=20)
    private String socketInfo;

    @Getter
    private Boolean ddr4;

    @Getter
    private Boolean ddr5;

    @Column(name = "integrated_graphics")
    private Boolean integratedGraphics;

    @Column(name = "cooler_included")
    private Boolean coolerIncluded;

    @Getter
    @Column(name = "single_score")
    private int singleScore;

    @Column(name = "multi_score")
    private int multiScore;

    @Setter
    @Column(name = "`class`", columnDefinition = "tinyint")
    private Integer classColumn;
}
