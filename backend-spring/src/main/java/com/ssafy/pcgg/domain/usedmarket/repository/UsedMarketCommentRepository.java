package com.ssafy.pcgg.domain.usedmarket.repository;

import com.ssafy.pcgg.domain.usedmarket.entity.UsedMarketComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedMarketCommentRepository extends JpaRepository<UsedMarketComment, Long> {
}
