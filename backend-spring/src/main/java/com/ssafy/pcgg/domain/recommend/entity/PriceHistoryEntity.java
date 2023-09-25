package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class PriceHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private PartTypeNsEntity type;

    @NotNull
    @Column(name="part_id")
    private Long partId;

    @NotNull
    @CreationTimestamp
    @Column(name="changed_date")
    private LocalDateTime changedDate;

    @NotNull
    private Integer price;
}
