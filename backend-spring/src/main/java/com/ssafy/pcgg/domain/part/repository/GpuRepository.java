package com.ssafy.pcgg.domain.part.repository;

import com.ssafy.pcgg.domain.part.entity.GpuEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GpuRepository extends JpaRepository<GpuEntity, Long> {

    List<GpuEntity> findAllByClassColumn(Integer classColumn);

    Slice<GpuEntity> findSliceByNameContaining(Pageable pageable, String keyword);

    List<GpuEntity> findAllByClassColumnAndPriceLessThanEqual(int partClass, int budget);

    Slice<GpuEntity> findSliceBy(Pageable pageable);

    @Query("select g " +
            "from GpuEntity g " +
            "where g.classColumn = :clas " +
                "and g.price != 0 " +
                "and g.score !=0 " +
                "and g.neededPower != 0 " +
            "order by g.classColumn, g.price")
    List<GpuEntity> findByClassColumnAndPriceAndScore(@Param("clas")int classColumn);

    @Query("select g " +
            "from GpuEntity g " +
            "where g.classColumn != 0 " +
            "and g.price != 0 " +
            "and g.score !=0 " +
            "and g.neededPower != 0 " +
            "order by g.classColumn, g.price")
    List<GpuEntity> findAllByClassColumnAndPriceAndScore();
}
