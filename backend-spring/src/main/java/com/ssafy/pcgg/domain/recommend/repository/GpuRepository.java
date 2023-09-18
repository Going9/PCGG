package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.GpuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpuRepository extends JpaRepository<GpuEntity,Long> {
    
}
