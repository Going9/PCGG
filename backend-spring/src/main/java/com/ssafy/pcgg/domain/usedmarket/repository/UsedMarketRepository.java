package com.ssafy.pcgg.domain.usedmarket.repository;

import com.ssafy.pcgg.domain.usedmarket.entity.UsedMarket;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedMarketRepository extends JpaRepository<UsedMarket, Long> {

  List<UsedMarket> findAllByTitleContainingIgnoreCaseOrderByIdDesc(String keyword, Pageable pageable);
}
