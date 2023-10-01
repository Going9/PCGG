package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.ChassisEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ChassisRepository extends JpaRepository<ChassisEntity, Long> {
    /*
    3. Chassis는
        mainboard의 size에 따른 chassis의 ~_atx컬럼이 true여야 하고
        gpu의 길이 < chassis의 depth 여야한다.
     */
    @Query("SELECT c " +
            "FROM ChassisEntity c " +
            "WHERE " +
                "CASE " +
                    "WHEN :caseSize='mini_itx' THEN c.miniItx " +
                    "WHEN :caseSize='micro_atx' THEN c.microAtx " +
                    "WHEN :caseSize='standard_atx' THEN c.standardAtx " +
                    "WHEN :caseSize='extended_atx' THEN c.extendedAtx " +
                    "ELSE FALSE " +
                "END " +
            "AND c.maxGpuDepth > :width")
    List<ChassisEntity> findByCaseSizeAndDepth(@Param("caseSize")String caseSize, @Param("width")BigDecimal width);

    Slice<ChassisEntity> findSliceByNameContaining(Pageable pageable, String keyword);

    Slice<ChassisEntity> findSliceBy(Pageable pageable);
}
