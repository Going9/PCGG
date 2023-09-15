package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "price_history")
public class PriceHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;

    private Integer partId;

    @CreationTimestamp
    private LocalDate start_date;
    private LocalDate end_date;
}
