package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.GpuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GpuRepository extends JpaRepository<GpuEntity,Long> {

    List<GpuEntity> findAllByClass(Integer classColumn);
}
