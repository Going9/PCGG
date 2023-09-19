package com.ssafy.pcgg.domain.recommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.recommend.entity.ChassisEntity;

@Repository
public interface ChassisRepository extends JpaRepository<ChassisEntity, Integer> {
}
