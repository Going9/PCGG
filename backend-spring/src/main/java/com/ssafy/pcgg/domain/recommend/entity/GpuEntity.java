package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "part_gpu")
public class GpuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(length = 100)
    private String name;

    @Getter
    private Integer price;

    @Column(name="image_source", length=100)
    private String imageSource;

    private Boolean extinct;

    @CreationTimestamp
    //@Column(name="changed_date", columnDefinition="DATE DEFAULT CURRENT_DATE")
    @Column(name="changed_date", nullable = false)
    private LocalDate changedDate;

    @Column(name = "needed_power")
    private Boolean neededPower;

    @Getter
    @Column(columnDefinition = "DECIMAL(7,2)")
    //@Column(precision = 7, scale = 2)
    private BigDecimal width;

    @Column(columnDefinition = "DECIMAL(7,2)")
    private BigDecimal thickness;

    @Setter
    @Column(name = "`class`", columnDefinition = "tinyint")
    private Integer classColumn;
}
