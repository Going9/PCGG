package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "part_type_ns")
public class PartTypeNsEntity {

    @Id
    @Column(length = 10)
    private String name;
}
