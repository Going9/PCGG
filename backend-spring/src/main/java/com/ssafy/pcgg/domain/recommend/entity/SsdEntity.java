package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "part_ssd")
public class SsdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Getter
    private Integer price;

    @Column(name="image_source", length=100)
    private String imageSource;

    private Boolean extinct;

    @Getter
    @Column(name="pcie_ver")
    private Integer pcieVer;

    @Column(name="reading_speed")
    private Integer readingSpeed;

    @Column(name="writing_speed")
    private Integer writingSpeed;

    @Column(length = 20)
    private String manufacturer;
}
