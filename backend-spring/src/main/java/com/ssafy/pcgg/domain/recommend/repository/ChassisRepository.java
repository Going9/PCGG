package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.ChassisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChassisRepository extends JpaRepository<ChassisEntity,Long> {
    
}
