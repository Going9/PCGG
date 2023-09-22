package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "part_ram")
public class RamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Column(name = "memory_spec", length = 10)
    private String memorySpec;

    @Column(name = "memory_clock")
    private Integer memoryClock;

    @Column(name = "heat_sink")
    private Boolean heatSink;

    @Setter
    @Column(name = "class", columnDefinition = "tinyint")
    private Integer classColumn;
}
