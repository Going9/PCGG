package com.ssafy.pcgg.domain.part.repository;

import com.ssafy.pcgg.domain.part.entity.UsageNsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsageNsRepository extends JpaRepository<UsageNsEntity,String> {
}
