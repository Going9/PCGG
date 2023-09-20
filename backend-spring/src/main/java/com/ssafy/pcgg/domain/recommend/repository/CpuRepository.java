package com.ssafy.pcgg.domain.recommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.recommend.entity.CpuEntity;

@Repository
public interface CpuRepository extends JpaRepository<CpuEntity, Long> {
}
