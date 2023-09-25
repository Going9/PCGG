package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.PartTypeNsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PartTypeNsRepository extends JpaRepository<PartTypeNsEntity,String> {
    PartTypeNsEntity findByName(String mainboard);
}
