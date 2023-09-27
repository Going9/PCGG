package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.RamEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RamRepository extends JpaRepository<RamEntity, Long> {

    List<RamEntity> findAllByClassColumn(Integer classColumn);

    Slice<RamEntity> findSliceByNameContaining(Pageable pageable, String keyword);

}
