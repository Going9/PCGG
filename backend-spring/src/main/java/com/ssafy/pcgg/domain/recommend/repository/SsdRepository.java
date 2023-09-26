package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.SsdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SsdRepository extends JpaRepository<SsdEntity,Long> {

    List<SsdEntity> findByCapacity(BigDecimal ssdSize);
}
