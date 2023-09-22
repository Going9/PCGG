package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface QuoteRepository extends JpaRepository<QuoteEntity,Long>{

}
