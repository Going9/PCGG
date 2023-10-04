package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.SsdEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SsdRepository extends JpaRepository<SsdEntity, Long> {

    List<SsdEntity> findByCapacity(BigDecimal ssdSize);

    Slice<SsdEntity> findSliceByNameContaining(Pageable pageable, String keyword);

    @Query("SELECT s FROM SsdEntity s WHERE s.price < :budget")
    List<SsdEntity> findAllByBudget(@Param("budget") int budget);

    Slice<SsdEntity> findSliceBy(Pageable pageable);
}
