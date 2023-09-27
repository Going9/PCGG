package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "part_gpu")
public class GpuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Getter
    @Column(length = 100)
    private String name;

    // @Getter
    private Integer price;

    @Column(name="image_source", length=200)
    private String imageSource;

    private Boolean extinct;

    @Column(name = "needed_power")
    private Integer neededPower;

    // @Getter
    @Column(columnDefinition = "DECIMAL(7,2)")
    //@Column(precision = 7, scale = 2)
    private BigDecimal width;

    @Column(columnDefinition = "DECIMAL(7,2)")
    private BigDecimal thickness;

    private Integer score;

    @Setter
    @Column(name = "`class`", columnDefinition = "tinyint")
    private Integer classColumn;

    public Integer getNeededPower() {
        return neededPower;
    }

    public void setNeededPower(Integer neededPower) {
        this.neededPower = neededPower;
    }
}
