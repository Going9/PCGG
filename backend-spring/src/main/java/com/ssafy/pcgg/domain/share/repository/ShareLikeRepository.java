package com.ssafy.pcgg.domain.share.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.share.entity.Share;
import com.ssafy.pcgg.domain.share.entity.ShareLike;
import com.ssafy.pcgg.domain.user.UserEntity;

@Repository
public interface ShareLikeRepository  extends JpaRepository<ShareLike, Long> {
	ShareLike findByShareAndUser(Share share, UserEntity userEntity);
}
