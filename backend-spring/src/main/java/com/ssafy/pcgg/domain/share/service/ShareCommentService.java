package com.ssafy.pcgg.domain.share.service;

import org.springframework.stereotype.Service;

import com.ssafy.pcgg.domain.share.dto.CommentsAddRequestDto;
import com.ssafy.pcgg.domain.share.entity.Share;
import com.ssafy.pcgg.domain.share.entity.ShareComment;
import com.ssafy.pcgg.domain.share.repository.ShareCommentRepository;
import com.ssafy.pcgg.domain.share.repository.ShareRepository;
import com.ssafy.pcgg.domain.user.UserEntity;
import com.ssafy.pcgg.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShareCommentService {

	private final ShareRepository shareRepository;
	private final ShareCommentRepository shareCommentRepository;
	private final UserRepository userRepository;

	public Long writeComment(Long articleId, CommentsAddRequestDto addRequestDto){
		Share share = shareRepository.findById(articleId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 공유마당 게시글이 존재하지 않습니다."));
		UserEntity userEntity = userRepository.findById(addRequestDto.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 사용자가 존재하지 않습니다."));

		ShareComment shareComment = ShareComment.builder()
			.share(share)
			.user(userEntity)
			.content(addRequestDto.getContent())
			.build();

		ShareComment comment = shareCommentRepository.save(shareComment);

		return comment.getId();
	}
}
