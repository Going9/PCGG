package com.ssafy.pcgg.domain.recommend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.ssafy.pcgg.domain.part.entity.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quote")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="cpu_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    private CpuEntity cpu;

    @ManyToOne
    @JoinColumn(name="mainboard_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    private MainboardEntity mainboard;

    @ManyToOne
    @JoinColumn(name="ssd_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    private SsdEntity ssd;

    @ManyToOne
    @JoinColumn(name="ram_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    private RamEntity ram;

    @ManyToOne
    @JoinColumn(name="gpu_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    private GpuEntity gpu;

    @ManyToOne
    @JoinColumn(name="chassis_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    private ChassisEntity chassis;

    @ManyToOne
    @JoinColumn(name="power_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    private PowerEntity power;

    @ManyToOne
    @JoinColumn(name="cooler_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), insertable = false, updatable = false)
    private CoolerEntity cooler;

    // @Column(name = "total_price")
    // private Integer totalPrice;

    @Column(name="cpu_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long cpuId;

    @Column(name = "mainboard_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long mainboardId;

    @Column(name="ssd_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long ssdId;

    @Column(name="ram_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long ramId;

    @Column(name="gpu_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long gpuId;

    @Column(name="chassis_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long chassisId;

    @Column(name="power_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long powerId;

    @Column(name="cooler_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long coolerId;

}
