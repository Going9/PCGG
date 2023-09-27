package com.ssafy.pcgg.domain.share.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.pcgg.domain.auth.UserIdDto;
import com.ssafy.pcgg.domain.share.dto.CommentResponseDto;
import com.ssafy.pcgg.domain.share.dto.CommentRequestDto;
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

	@Transactional
	public Long addComment(UserIdDto userId, Long articleId, CommentRequestDto addRequestDto){
		Share share = shareRepository.findById(articleId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 공유마당 게시글이 존재하지 않습니다."));
		UserEntity userEntity = userRepository.findById(userId.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 사용자가 존재하지 않습니다."));

		ShareComment shareComment = ShareComment.builder()
			.share(share)
			.user(userEntity)
			.content(addRequestDto.getContent())
			.createdAt(LocalDateTime.now())
			.build();

		return shareCommentRepository.save(shareComment).getId();
	}

	@Transactional(readOnly=true)
	public Slice<CommentResponseDto> getAllComments(int pages, Long articleId) {
		PageRequest pageRequest = PageRequest.of(pages, 30, Sort.by(Sort.Direction.DESC, "createdAt"));
		Slice<ShareComment> shareComments = shareCommentRepository.findByShareId(articleId, pageRequest);
		return shareComments.map(this::convertToDto);
	}

	private CommentResponseDto convertToDto(ShareComment shareComment){
		CommentResponseDto commentResponseDto = CommentResponseDto.builder()
			.id(shareComment.getId())
			.shareId(shareComment.getShare().getId())
			.userId(shareComment.getUser().getUserId())
			.userNickname(shareComment.getUser().getNickname())
			.content(shareComment.getContent())
			.build();

		return commentResponseDto;
	}

	@Transactional
	public void updateComment(UserIdDto userId, Long commentId, CommentRequestDto commentRequestDto){
		ShareComment shareComment = shareCommentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 공유마당 댓글이 존재하지 않습니다."));

		if(!Objects.equals(shareComment.getUser().getUserId(), userId.getUserId())) {
			throw new IllegalArgumentException("수정 권한이 없는 사용자입니다. (작성자만 수정가능)");
		}

		shareComment.updateContent(commentRequestDto.getContent());
		shareCommentRepository.save(shareComment);

	}

	@Transactional
	public void deleteComment(UserIdDto userId, Long commentId){
		ShareComment shareComment = shareCommentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id의 공유마당 댓글이 존재하지 않습니다."));

		if(!Objects.equals(shareComment.getUser().getUserId(), userId.getUserId())) {
			throw new IllegalArgumentException("삭제 권한이 없는 사용자입니다. (작성자만 삭제가능)");
		}

		shareCommentRepository.delete(shareComment);
	}
}
