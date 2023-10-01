package com.ssafy.pcgg.domain.part.dto;

import com.ssafy.pcgg.domain.recommend.exception.QuoteCandidateException;
import com.ssafy.pcgg.domain.recommend.util.PerformanceRequirement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class MainboardResponseDto extends PartDto {
    private Long id;
    private Boolean extinct;
    private String socketInfo;
    private String grade;
    private String memorySpec;
    private String size;
    private Boolean pcie3;
    private Boolean pcie4;
    private Boolean pcie5;
    private Integer m2Count;
    private Integer classColumn;
}
