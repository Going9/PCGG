package com.ssafy.pcgg.domain.share.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.share.entity.Share;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
	Slice<Share> findSliceBy(Pageable pageable);

	Slice<Share> findSliceByTitleContaining(Pageable pageable, String keyword);

	List<Share> findByUser_UserId(Long userId);

	@Query("SELECT s FROM Share s "
		+ "WHERE s.id IN (SELECT sl.share.id "
		+ "FROM ShareLike sl "
		+ "WHERE sl.mark = 1 "
		+ "GROUP BY sl.share.id "
		+ "ORDER BY SUM(sl.mark) DESC) "
		+ "ORDER BY s.id")
	List<Share> findTop5SharesWithMostLikes();
}
