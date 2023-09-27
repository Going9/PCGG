package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.CoolerEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoolerRepository extends JpaRepository<CoolerEntity, Long> {
	Slice<CoolerEntity> findSliceByNameContaining(Pageable pageable, String name);
}
