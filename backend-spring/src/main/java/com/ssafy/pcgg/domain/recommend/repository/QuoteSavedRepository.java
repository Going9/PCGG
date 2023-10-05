package com.ssafy.pcgg.domain.recommend.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.recommend.entity.QuoteEntity;
import com.ssafy.pcgg.domain.recommend.entity.QuoteSaved;

@Repository
public interface QuoteSavedRepository extends JpaRepository<QuoteSaved, Long> {

	@Query("SELECT qs.quote FROM QuoteSaved qs WHERE qs.userId = :userId")
	Slice<QuoteEntity> findQuotesByUserId(@Param("userId") Long userId, Pageable pageable);

	QuoteSaved findQuotesByUserIdAndQuoteId(Long userId, Long quoteId);
}
