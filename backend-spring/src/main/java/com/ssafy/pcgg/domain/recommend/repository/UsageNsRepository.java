package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.PowerEntity;
import com.ssafy.pcgg.domain.recommend.entity.UsageNsEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsageNsRepository extends JpaRepository<UsageNsEntity,String> {
}
