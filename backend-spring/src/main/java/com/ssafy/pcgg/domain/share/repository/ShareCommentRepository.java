package com.ssafy.pcgg.domain.share.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.share.entity.ShareComment;

@Repository
public interface ShareCommentRepository extends JpaRepository<ShareComment, Long> {

	Slice<ShareComment> findByShareId(Long shareId, Pageable pageable);
}
