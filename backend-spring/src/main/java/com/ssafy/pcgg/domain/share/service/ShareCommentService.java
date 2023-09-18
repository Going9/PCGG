package com.ssafy.pcgg.domain.share.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

	public Long writeComment(Long articleId, CommentRequestDto addRequestDto){
		Share share = shareRepository.findById(articleId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 공유마당 게시글이 존재하지 않습니다."));
		UserEntity userEntity = userRepository.findById(addRequestDto.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 사용자가 존재하지 않습니다."));

		ShareComment shareComment = ShareComment.builder()
			.share(share)
			.user(userEntity)
			.content(addRequestDto.getContent())
			.createdAt(LocalDateTime.now())
			.build();

		ShareComment comment = shareCommentRepository.save(shareComment);

		return comment.getId();
	}

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
			// .userName(shareComment.getUser().getNickname())	//추후 UserEntity에 nickname 추가시, 수정예정
			.content(shareComment.getContent())
			.build();

		return commentResponseDto;
	}

	public void deleteComments(Long commentId){
		// TODO: 댓글 작성자와 삭제 요청자의 일치 여부 확인

		ShareComment shareComment = shareCommentRepository.findById(commentId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 공유마당 댓글이 존재하지 않습니다."));

		shareCommentRepository.delete(shareComment);
	}
}
