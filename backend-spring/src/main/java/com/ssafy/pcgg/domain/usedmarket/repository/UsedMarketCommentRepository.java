package com.ssafy.pcgg.domain.usedmarket.repository;

import com.ssafy.pcgg.domain.usedmarket.entity.UsedMarketComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsedMarketCommentRepository extends JpaRepository<UsedMarketComment, Long> {
    List<UsedMarketComment> findAllByUsedMarketId(Long usedMarketId);
}
