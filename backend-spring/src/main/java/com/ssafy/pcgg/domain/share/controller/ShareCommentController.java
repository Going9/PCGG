package com.ssafy.pcgg.domain.share.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pcgg.domain.share.dto.CommentResponseDto;
import com.ssafy.pcgg.domain.share.dto.CommentRequestDto;
import com.ssafy.pcgg.domain.share.service.ShareCommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Shares Comments", description = "공유마당 댓글 API")
@RestController
@RequestMapping("/api/v1/shares")
@RequiredArgsConstructor
public class ShareCommentController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ShareCommentService shareCommentService;

	@Operation(summary = "공유마당 댓글 작성", description = "공유마당 게시글에 댓글을 작성합니다.")
	@PostMapping("/{articleId}/comments")
	public ResponseEntity<Long> addComments(@PathVariable Long articleId, @RequestBody CommentRequestDto addRequestDto) {
		logger.info("addComments(), articleId = {}", articleId);
		Long commentId = shareCommentService.writeComment(articleId, addRequestDto);
		return ResponseEntity.ok().body(commentId);
	}

	@Operation(summary = "공유마당 댓글 조회", description = "공유마당 게시글의 댓글들을 조회합니다.")
	@GetMapping("/{articleId}/comments")
	public ResponseEntity<Slice<CommentResponseDto>> getComments(@PathVariable Long articleId, @RequestParam(value = "pages", defaultValue = "0") int pages) {
		logger.info("addComments(), pages = {}", pages);
		Slice<CommentResponseDto> commentResponseDtos = shareCommentService.getAllComments(pages, articleId);
		return ResponseEntity.ok().body(commentResponseDtos);
	}

	@Operation(summary = "공유마당 댓글 수정", description = "공유마당 게시글에 댓글을 수정합니다.")
	@PutMapping("/comments/{commentId}")
	public ResponseEntity<Void> updateComments(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
		logger.info("updateComments(), commentId = {}", commentId);
		shareCommentService.updateComments(commentId, commentRequestDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "공유마당 댓글 삭제", description = "공유마당 게시글에 댓글을 삭제합니다.")
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<Void> deleteComments(@PathVariable Long commentId) {
		logger.info("deleteComments(), commentId = {}", commentId);
		shareCommentService.deleteComments(commentId);
		return ResponseEntity.ok().build();
	}

}
