package com.ssafy.pcgg.domain.part.repository;

import com.ssafy.pcgg.domain.part.entity.RamEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RamRepository extends JpaRepository<RamEntity, Long> {

    List<RamEntity> findAllByClassColumn(Integer classColumn);

    List<RamEntity> findAllByClassColumnAndPriceLessThanEqual(Integer classColumn, Integer price);


    Slice<RamEntity> findSliceByNameContaining(Pageable pageable, String keyword);

    Slice<RamEntity> findSliceBy(Pageable pageable);

    @Query("select r " +
            "from RamEntity r " +
            "where r.classColumn = :clas " +
                "and r.price != 0 " +
                "and r.capacity != 0 " +
            "order by r.capacity, r.price")
    List<RamEntity> findByClassColumnAndPriceAndCapacity(@Param("clas")int classColumn);

    @Query("select r " +
            "from RamEntity r " +
            "where r.classColumn != 0 " +
            "and r.price != 0 " +
            "and r.capacity != 0 " +
            "order by r.capacity, r.price")
    List<RamEntity> findAllByClassColumnAndPriceAndCapacity();
}
