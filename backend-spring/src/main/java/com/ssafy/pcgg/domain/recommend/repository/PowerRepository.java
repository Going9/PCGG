package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.PowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowerRepository extends JpaRepository<PowerEntity,Long> {

    List<PowerEntity> findAllByClassColumn(Integer classColumn);
}
