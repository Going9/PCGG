package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.dto.LaptopResponseDto;
import com.ssafy.pcgg.domain.recommend.entity.LaptopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LaptopRepository extends JpaRepository<LaptopEntity,Long> {

    @Query("SELECT l " +
            "FROM LaptopEntity l " +
            "WHERE " +
            "CASE :os " +
            "WHEN true THEN (l.os != '미포함') " +
            "WHEN false THEN (l.os = '미포함') " +
            "END " +
            "AND l.price < :budget")
    List<LaptopResponseDto> findByOsAndBudget(@Param("os")boolean os, @Param("budget")int budget);
}
