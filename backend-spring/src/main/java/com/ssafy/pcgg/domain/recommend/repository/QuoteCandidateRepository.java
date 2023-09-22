package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.QuoteCandidateEntity;
import com.ssafy.pcgg.domain.recommend.entity.UsageNsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface QuoteCandidateRepository extends JpaRepository<QuoteCandidateEntity,Long>{

    void deleteByUsage(UsageNsEntity usage);
}
