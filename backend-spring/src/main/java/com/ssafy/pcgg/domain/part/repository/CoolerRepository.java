package com.ssafy.pcgg.domain.part.repository;

import com.ssafy.pcgg.domain.part.entity.CoolerEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoolerRepository extends JpaRepository<CoolerEntity, Long> {
	Slice<CoolerEntity> findSliceByNameContaining(Pageable pageable, String name);

	Slice<CoolerEntity> findSliceBy(Pageable pageable);

    @Query("SELECT c FROM CoolerEntity c WHERE c.freeWarrantyPeriod > 0 AND c.price < :budget")
    List<CoolerEntity> findAllByWarrantyPeriodAndBudget(@Param("budget")int budget);
}
