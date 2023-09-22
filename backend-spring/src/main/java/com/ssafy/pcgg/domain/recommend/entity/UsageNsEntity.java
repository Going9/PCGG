package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "usage_ns")
public class UsageNsEntity {

    @Id
    @Getter
    @Column(length = 10)
    private String name;
}
