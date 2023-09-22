package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.CoolerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoolerRepository extends JpaRepository<CoolerEntity,Long> {
    
}
