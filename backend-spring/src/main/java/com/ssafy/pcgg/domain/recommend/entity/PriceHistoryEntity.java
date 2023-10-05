package com.ssafy.pcgg.domain.recommend.entity;

import com.ssafy.pcgg.domain.part.entity.PartTypeNsEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Table(name = "price_history")
@Entity
public class PriceHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="type")
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
