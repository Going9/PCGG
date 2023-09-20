package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.PowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PowerRepository extends JpaRepository<PowerEntity,Long> {

    List<?> findAllByClass(Integer classNumber);
}
