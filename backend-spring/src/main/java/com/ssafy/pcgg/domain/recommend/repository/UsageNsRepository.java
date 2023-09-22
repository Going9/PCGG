package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.PowerEntity;
import com.ssafy.pcgg.domain.recommend.entity.UsageNsEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsageNsRepository extends JpaRepository<UsageNsEntity,String> {
    UsageNsEntity findByName(String s);
}
