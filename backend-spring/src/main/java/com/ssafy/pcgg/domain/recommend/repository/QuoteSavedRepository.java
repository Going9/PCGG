package com.ssafy.pcgg.domain.recommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.recommend.entity.QuoteSaved;

@Repository
public interface QuoteSavedRepository extends JpaRepository<QuoteSaved, Long> {
}
