package com.ssafy.pcgg.domain.recommend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "part_ssd")
public class SsdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String name;

    private Integer price;

    @Column(name="image_source", length=100)
    private String imageSource;

    private Boolean extinct;

    @CreationTimestamp
    //@Column(name="changed_date", columnDefinition="DATE DEFAULT CURRENT_DATE")
    @Column(name="changed_date", nullable = false)
    private LocalDate changedDate;

    @Column(name="pcie_ver")
    private Integer pcieVer;

    @Column(name="reading_speed")
    private Integer readingSpeed;

    @Column(name="writing_speed")
    private Integer writingSpeed;

    @OneToMany(mappedBy = "ssd")
    List<QuoteCandidateEntity> quoteCandidate;

    @OneToMany(mappedBy = "ssd")
    List<QuoteEntity> quote;
}
