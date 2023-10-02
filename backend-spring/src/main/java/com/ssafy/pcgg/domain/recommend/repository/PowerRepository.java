package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.PowerEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PowerRepository extends JpaRepository<PowerEntity, Long> {

    List<PowerEntity> findAllByClassColumn(Integer classColumn);

    @Query("SELECT p FROM PowerEntity p WHERE p.classColumn = :class AND p.depth < :maxPowerDepth")
    List<PowerEntity> findByChassisAndClass(@Param("maxPowerDepth") BigDecimal maxPowerDepth, @Param("class")int classColumn);

    Slice<PowerEntity> findSliceByNameContaining(Pageable pageable, String keyword);

    Slice<PowerEntity> findSliceBy(Pageable pageable);
}
