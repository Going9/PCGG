package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.MainboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainboardRepository extends JpaRepository<MainboardEntity,Long> {
    
}
