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

import com.ssafy.pcgg.domain.share.dto.ShareAddRequestDto;
import com.ssafy.pcgg.domain.share.dto.ShareMarkRequestDto;
import com.ssafy.pcgg.domain.share.dto.ShareResponseDto;
import com.ssafy.pcgg.domain.share.service.ShareService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Shares", description = "공유마당 API")
@RestController
@RequestMapping("/api/v1/shares")
@RequiredArgsConstructor
public class ShareController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ShareService shareService;

	@Operation(summary = "공유마당 게시글 작성", description = "공유마당 게시글을 작성합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
		@ApiResponse(responseCode = "404", description = "NOT FOUND"),
		@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
	})
	@PostMapping("/")
	public ResponseEntity<Long> addShare(@RequestBody ShareAddRequestDto addRequestDto) {
		logger.info("addShare(), userId = {}", addRequestDto.getUserId());

		Long quoteId = shareService.addShare(addRequestDto);

		return ResponseEntity.ok().body(quoteId);
	}

	@Operation(summary = "공유마당 게시글 상세조회", description = "공유마당 게시글을 조회합니다.")
	@GetMapping("/{articleId}")
	public ResponseEntity<?> getShareDetail(@PathVariable Long articleId) {
		logger.info("getShareDetail(), articleId = {}", articleId);
		ShareResponseDto shareResponseDto = shareService.getShare(articleId);
		return ResponseEntity.ok().body(shareResponseDto);
	}

	@Operation(summary = "공유마당 게시글 삭제", description = "공유마당 게시글을 삭제합니다.")
	@DeleteMapping("/{articleId}")
	public ResponseEntity<?> deleteShare(@PathVariable Long articleId) {
		logger.info("deleteShare(), articleId = {}", articleId);
		shareService.deleteShare(articleId);
		return ResponseEntity.ok().body(articleId);
	}

	@Operation(summary = "공유마당 게시글 목록 조회", description = "공유마당 게시글 목록을 조회합니다.")
	@GetMapping("/")
	public ResponseEntity<Slice<ShareResponseDto>> getShares(@RequestParam(value = "pages", defaultValue = "0") int pages) {
		logger.info("getShares(), page = {}", pages);
		Slice<ShareResponseDto> shareResponseDto = shareService.getAllShare(pages);
		return ResponseEntity.ok().body(shareResponseDto);
	}

	@Operation(summary = "공유마당 게시글 좋아요/싫어요", description = "공유마당 게시글에 좋아요/싫어요를 누릅니다.")
	@PutMapping("/{articleId}/mark")
	public ResponseEntity<?> markLikes(@PathVariable Long articleId, @RequestBody ShareMarkRequestDto markRequestDto) {
		logger.info("markLikes(), articleId = {}", articleId);
		shareService.markLikes(articleId, markRequestDto);
		return ResponseEntity.ok().body(markRequestDto.getMark());
	}

}
