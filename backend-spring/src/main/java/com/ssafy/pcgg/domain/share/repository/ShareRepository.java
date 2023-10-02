package com.ssafy.pcgg.domain.share.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.share.entity.Share;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
	Slice<Share> findSliceBy(Pageable pageable);

	List<Share> findByUser_UserId(Long userId);
}
