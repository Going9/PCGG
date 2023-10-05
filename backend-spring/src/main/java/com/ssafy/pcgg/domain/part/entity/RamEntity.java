package com.ssafy.pcgg.domain.part.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Table(name = "part_ram")
public class RamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    private Integer price;

    @Column(name="image_source", length=200)
    private String imageSource;

    private Boolean extinct;

    @Column(name = "memory_spec", length = 10)
    private String memorySpec;

    @Column(name = "memory_clock")
    private Integer memoryClock;

    @Column(name = "heat_sink")
    private Boolean heatSink;

    @Setter
    @Column(name = "`class`", columnDefinition = "tinyint")
    private Integer classColumn;

    private Integer capacity;
}
