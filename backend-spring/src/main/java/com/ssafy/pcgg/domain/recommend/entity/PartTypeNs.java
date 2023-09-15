package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="part_type_ns")
public class PartTypeNs {
    @Id
    @Column(length=10)
    @NotNull
    private String name;
}
