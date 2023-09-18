package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
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

    private Integer price;

    @Column(name="image_source", length=100)
    private String imageSource;

    private Boolean extinct;

    @CreationTimestamp
    //@Column(name="changed_date", columnDefinition="DATE DEFAULT CURRENT_DATE")
    @Column(name="changed_date", nullable = false)
    private LocalDate changedDate;

    @Column(name="socket_info", length=20)
    private String socketInfo;

    private Boolean ddr4;
    private Boolean ddr5;

    @Column(name = "integrated_graphics")
    private Boolean integratedGraphics;

    @Column(name = "cooler_included")
    private Boolean coolerIncluded;
}
