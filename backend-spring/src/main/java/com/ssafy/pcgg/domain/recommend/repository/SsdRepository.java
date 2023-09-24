package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.SsdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SsdRepository extends JpaRepository<SsdEntity,Long> {
    
}
