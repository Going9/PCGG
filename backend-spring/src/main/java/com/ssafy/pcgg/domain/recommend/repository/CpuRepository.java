package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.CpuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CpuRepository extends JpaRepository<CpuEntity,Long> {

    List<CpuEntity> findAllByClass(Integer classNumber);
}
