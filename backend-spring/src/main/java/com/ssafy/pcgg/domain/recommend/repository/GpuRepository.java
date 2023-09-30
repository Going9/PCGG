package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.GpuEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GpuRepository extends JpaRepository<GpuEntity, Long> {

    List<GpuEntity> findAllByClassColumn(Integer classColumn);

    Slice<GpuEntity> findSliceByNameContaining(Pageable pageable, String keyword);

    Slice<GpuEntity> findSliceBy(Pageable pageable);
}
