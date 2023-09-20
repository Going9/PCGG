package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.RamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RamRepository extends JpaRepository<RamEntity,Long> {

    List<?> findAllByClass(Integer classNumber);
}
