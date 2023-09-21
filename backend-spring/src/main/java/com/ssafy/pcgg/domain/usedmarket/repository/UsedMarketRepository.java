package com.ssafy.pcgg.domain.usedmarket.repository;

import com.ssafy.pcgg.domain.usedmarket.entity.UsedMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedMarketRepository extends JpaRepository<UsedMarket, Long> {

}
