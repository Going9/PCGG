package com.ssafy.pcgg.domain.share.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.share.entity.Share;
import com.ssafy.pcgg.domain.share.entity.ShareLike;
import com.ssafy.pcgg.domain.user.UserEntity;

import java.util.List;

@Repository
public interface ShareLikeRepository extends JpaRepository<ShareLike, Long> {
	ShareLike findByShareAndUser(Share share, UserEntity userEntity);

	@Query("SELECT COUNT(sl.id) FROM ShareLike sl WHERE sl.share.id = :shareId AND sl.mark = :mark")
	long countLikesForShareWithId(@Param("shareId") Long shareId, @Param("mark") int mark);

	@Query("SELECT sl.mark FROM ShareLike sl WHERE sl.share.id = :shareId AND sl.user.userId = :userId")
	Integer findMarkByShareIdAndUserId(@Param("shareId") Long shareId, @Param("userId") Long userId);

	List<ShareLike> findByUser_UserId(Long userId);
}
