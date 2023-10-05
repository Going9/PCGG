package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "laptop")
public class LaptopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String type;

    @Column(length = 20)
    private String os;

    @Column(name="screen_size")
    private Integer screenSize;

    @Column(name="refresh_rate")
    private Integer refreshRate;

    @Column(length = 20)
    private String resolution;

    private Integer brightness;

    @Column(length = 100)
    private String cpu;

    @Column(name = "ram_capacity")
    private Integer ramCapacity;

    @Column(name = "ram_upgradeable", columnDefinition = "tinyint(1)")
    private Boolean ramUpgradeable;

    @Column(length = 100)
    private String gpu;

    private Integer tgp;

    @Column(columnDefinition = "decimal(10,3)")
    private BigDecimal ssd;

    @Column(columnDefinition = "tinyint(1)")
    private Boolean cellular;

    @Column(columnDefinition = "tinyint(1)")
    private Boolean hdmi;

    @Column(columnDefinition = "tinyint(1)")
    private Boolean thunderbolt;

    @Column(name="usb_a")
    private Integer usbA;

    @Column(name="usb_c")
    private Integer usbC;

    @Column(name = "sd_card", length = 20)
    private String sdCard;

    @Column(columnDefinition = "decimal(7,2)")
    private BigDecimal weight;

    @Column(columnDefinition = "decimal(7,2)")
    private BigDecimal battery;

    @Column(length = 100)
    private String name;

    private Integer price;

    @Column(name = "image_sorce", length = 200)
    private String imageSource;

    @Column(columnDefinition = "tinyint(1)")
    private Boolean extinct;

    @Column(length = 20)
    private String manufacturer;
}
