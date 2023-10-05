package com.ssafy.pcgg.domain.part.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "usage_ns")
public class UsageNsEntity {

    @Id
    @Column(length = 10)
    private String name;
}
