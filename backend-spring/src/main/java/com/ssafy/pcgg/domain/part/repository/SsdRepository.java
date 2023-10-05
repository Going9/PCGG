package com.ssafy.pcgg.domain.part.repository;

import com.ssafy.pcgg.domain.part.entity.SsdEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SsdRepository extends JpaRepository<SsdEntity, Long> {

    Slice<SsdEntity> findSliceByNameContaining(Pageable pageable, String keyword);

    @Query("SELECT s FROM SsdEntity s WHERE s.price < :budget")
    List<SsdEntity> findAllByBudget(@Param("budget") int budget);

    Slice<SsdEntity> findSliceBy(Pageable pageable);

    @Query("SELECT s " +
            "FROM SsdEntity s " +
            "WHERE " +
            "s.capacity between :lower and :upper " +
            "and s.capacity != 0 " +
            "and s.readingSpeed != 0 " +
            "and s.writingSpeed != 0 " +
            "and s.price != 0")
    List<SsdEntity> findByCapacityBetween(@Param("lower")Double lower, @Param("upper")Double upper);
}
