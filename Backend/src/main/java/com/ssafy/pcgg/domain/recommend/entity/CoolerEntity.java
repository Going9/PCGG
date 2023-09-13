package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "part_cooler")
public class CoolerEntity {
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

    @Column(length = 20)
    private String form;

    @Column(length = 20)
    private String type;

    @Column(name = "free_warranty_period")
    private Integer freeWarrantyPeriod;

    @Column(precision = 7,scale = 2)
    private BigDecimal height;

    @Column(name = "fan_count")
    private Integer fanCount;

    @Column(name = "max_fan_noise", columnDefinition = "DECIMAL(7,2)")
    private BigDecimal maxFanNoise;

    @OneToMany(mappedBy = "cooler")
    List<QuoteCandidateEntity> quoteCandidate;

    @OneToMany(mappedBy = "cooler")
    List<QuoteEntity> quote;
}
