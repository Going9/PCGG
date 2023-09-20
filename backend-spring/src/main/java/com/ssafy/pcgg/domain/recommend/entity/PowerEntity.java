package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "part_power")
public class PowerEntity {
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

    @Column(length = 10)
    private String size;

    @Getter
    @Column(length = 20)
    private String grade;

    private Integer output;

    @Column(precision = 7, scale = 2)
    private BigDecimal depth;

    @Column(name = "free_warranty_period")
    private Integer freeWarrantyPeriod;

    @Setter
    @Column(name = "class", columnDefinition = "tinyint")
    private Integer classColumn;
}
