package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "part_chassis")
public class ChassisEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Null
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

    @Column(name = "extended_atx")
    private Boolean extendedAtx;

    @Column(name = "standard_atx")
    private Boolean standardAtx;

    @Column(name = "micro_atx")
    private Boolean microAtx;

    @Column(name = "mini_itx")
    private Boolean miniItx;

    @Column(columnDefinition = "DECIMAL(7,2)")
    private BigDecimal width;

    @Column(columnDefinition = "DECIMAL(7,2)")
    private BigDecimal depth;

    @Getter
    @Column(name="max_power_depth", columnDefinition = "DECIMAL(7,2)")
    //@Column(name="max_power_depth", precision = 7, scale = 2)
    private BigDecimal maxPowerDepth;

    @Column(name="max_gpu_depth", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal maxGpuDepth;

    @Column(name="max_cooler_depth", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal maxCoolerDepth;
}
