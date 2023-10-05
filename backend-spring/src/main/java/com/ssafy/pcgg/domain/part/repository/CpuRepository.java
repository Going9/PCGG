package com.ssafy.pcgg.domain.part.repository;

import com.ssafy.pcgg.domain.part.entity.CpuEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CpuRepository extends JpaRepository<CpuEntity, Long> {

    List<CpuEntity> findAllByClassColumn(Integer classColumn);

    List<CpuEntity> findAllByClassColumnAndPriceLessThanEqual(Integer classColumn, Integer price);

    Slice<CpuEntity> findSliceByNameContaining(Pageable pageable, String keyword);

    Slice<CpuEntity> findSliceBy(Pageable pageable);

    @Query("SELECT c " +
            "FROM CpuEntity c " +
            "WHERE c.classColumn = :classColumn " +
                "and c.price != 0 " +
                "and c.singleScore !=0 " +
                "and case " +
                    "when :classColumn = 1 then c.integratedGraphics " +
                    "else true " +
                "end " +
            "order by c.singleScore, c.price")
    List<CpuEntity> findByClassColumnAndPriceAndSingleScore(@Param("classColumn")int classColumn);

    @Query("SELECT c " +
            "FROM CpuEntity c " +
            "WHERE c.classColumn != 0 " +
            "and c.price != 0 " +
            "and c.singleScore !=0 " +
            "order by c.singleScore, c.price")
    List<CpuEntity> findAllByClassColumnAndPriceAndScore();
}
