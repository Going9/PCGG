package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.CpuEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CpuRepository extends JpaRepository<CpuEntity, Long> {

    List<CpuEntity> findAllByClassColumn(Integer classColumn);

    List<CpuEntity> findAllByClassColumnAndPriceLessThanEqual(Integer classColumn, Integer price);

    Slice<CpuEntity> findSliceByNameContaining(Pageable pageable, String keyword);

    Slice<CpuEntity> findSliceBy(Pageable pageable);
}
