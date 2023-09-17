package com.ssafy.pcgg.domain.share.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pcgg.domain.share.dto.CommentsAddRequestDto;
import com.ssafy.pcgg.domain.share.service.ShareCommentService;
import com.ssafy.pcgg.domain.share.service.ShareService;

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
	public ResponseEntity<?> addComments(@PathVariable Long articleId, @RequestBody CommentsAddRequestDto addRequestDto) {
		logger.info("addComments(), articleId = {}", articleId);

		Long commentId = shareCommentService.writeComment(articleId, addRequestDto);
		return ResponseEntity.ok().body(commentId);
	}

}
