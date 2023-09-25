package com.ssafy.pcgg.domain.recommend.entity;

import com.ssafy.pcgg.domain.recommend.exception.QuoteCandidateException;
import com.ssafy.pcgg.domain.recommend.util.PerformanceRequirement;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name="part_mainboard")
public class MainboardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Getter
    private Integer price;

    @Column(name="image_source", length=100)
    private String imageSource;

    private Boolean extinct;

    @CreationTimestamp
    //@Column(name="changed_date", columnDefinition="DATE DEFAULT CURRENT_DATE")
    @Column(name="changed_date", nullable = false)
    private LocalDate changedDate;

    @Column(length=20)
    private String socketInfo;

    @Column(length=20)
    private String grade;

    @Column(name="memory_spec", length=10)
    private String memorySpec;

    @Getter
    @Column(length=30)
    private String size;

    private Boolean pcie3;
    private Boolean pcie4;
    private Boolean pcie5;

    @Column(name = "m2_count")
    private Integer m2Count;

    @Column(name = "`class`", columnDefinition = "tinyint")
    private Integer classColumn;

    public void setClassByUsage(String usage){
        this.classColumn = switch(usage){
            case "가성비사무","저사양개발", "캐주얼게임" -> PerformanceRequirement.LOW;
            case "고성능사무", "중사양게임", "일반영상편집", "일반방송", "캐주얼게임방송" -> PerformanceRequirement.MIDDLE;
            case "고사양게임", "전문영상편집", "3d디자인", "고성능게임방송" -> PerformanceRequirement.HIGH;
            case "고사양개발" -> PerformanceRequirement.GOOD;
            default -> throw new QuoteCandidateException("용도 매칭되지 않음. usage_ns의 레코드 점검필요");
        };
    }
}
