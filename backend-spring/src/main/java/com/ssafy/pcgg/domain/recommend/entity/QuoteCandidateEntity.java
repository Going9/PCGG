package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quote_candidate")
public class QuoteCandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name="cpu_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpuEntity cpu;

    @ManyToOne
    @NotNull
    @JoinColumn(name="mainboard_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MainboardEntity mainboard;

    @ManyToOne
    @NotNull
    @JoinColumn(name="ssd_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private SsdEntity ssd;

    @ManyToOne
    @NotNull
    @JoinColumn(name="ram_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private RamEntity ram;

    @ManyToOne
    @NotNull
    @JoinColumn(name="gpu_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private GpuEntity gpu;

    @ManyToOne
    @NotNull
    @JoinColumn(name="chassis_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ChassisEntity chassis;

    @ManyToOne
    @NotNull
    @JoinColumn(name="power_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PowerEntity power;

    @ManyToOne
    @NotNull
    @JoinColumn(name="cooler_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CoolerEntity cooler;

    @Column(name = "bench_score")
    private Integer benchScore;

    @Column(name = "total_price")
    private Integer totalPrice;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="`usage`")
    private UsageNsEntity usage;
}
