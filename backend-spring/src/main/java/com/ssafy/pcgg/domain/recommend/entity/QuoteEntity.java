package com.ssafy.pcgg.domain.recommend.entity;

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
    @JoinColumn(name="cpu_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpuEntity cpu;

    @ManyToOne
    @JoinColumn(name="mainboard_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MainboardEntity mainboard;

    @ManyToOne
    @JoinColumn(name="ssd_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private SsdEntity ssd;

    @ManyToOne
    @JoinColumn(name="ram_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private RamEntity ram;

    @ManyToOne
    @JoinColumn(name="gpu_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private GpuEntity gpu;

    @ManyToOne
    @JoinColumn(name="chassis_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ChassisEntity chassis;

    @ManyToOne
    @JoinColumn(name="power_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PowerEntity power;

    @ManyToOne
    @JoinColumn(name="cooler_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CoolerEntity cooler;

    @Column(name = "total_price")
    private Integer totalPrice;
}
