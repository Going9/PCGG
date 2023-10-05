package com.ssafy.pcgg.domain.share.controller;

import java.util.List;

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

import com.ssafy.pcgg.domain.auth.CurrentUserId;
import com.ssafy.pcgg.domain.auth.UserIdDto;
import com.ssafy.pcgg.domain.share.dto.AuthorMarkInfoDto;
import com.ssafy.pcgg.domain.share.dto.ShareAddQuoteRequestDto;
import com.ssafy.pcgg.domain.share.dto.ShareAddRequestDto;
import com.ssafy.pcgg.domain.share.dto.ShareDetailDto;
import com.ssafy.pcgg.domain.share.dto.ShareMarkRequestDto;
import com.ssafy.pcgg.domain.share.dto.ShareResponseDto;
import com.ssafy.pcgg.domain.share.service.ShareService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "Shares", description = "공유마당 API")
@RestController
@RequestMapping("/api/v1/shares")
@RequiredArgsConstructor
public class ShareController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ShareService shareService;

	@Operation(summary = "공유마당 게시글 작성", description = "공유마당 게시글을 작성합니다.")
	@PostMapping("/")
	@CurrentUserId("userId")
	public ResponseEntity<Long> addShare(UserIdDto userId, HttpServletRequest request, @RequestBody ShareAddRequestDto addRequestDto) {
		logger.info("addShare(), userId = {}", userId.getUserId());

		Long quoteId = shareService.addShare(userId, addRequestDto);

		return ResponseEntity.ok().body(quoteId);
	}

	@Operation(summary = "공유마당 게시글 상세조회", description = "공유마당 게시글을 조회합니다.")
	@GetMapping("/{articleId}")
	public ResponseEntity<ShareDetailDto> getShareDetail(@PathVariable Long articleId) {
		logger.info("getShareDetail(), articleId = {}", articleId);
		ShareDetailDto shareDetailDto = shareService.getShare(articleId);
		return ResponseEntity.ok().body(shareDetailDto);
	}

	@Operation(summary = "공유마당 게시글 목록 조회", description = "공유마당 게시글 목록을 조회합니다.")
	@GetMapping("/")
	public ResponseEntity<Slice<ShareResponseDto>> getShares(@RequestParam(name = "q", required = false) String keyword, @RequestParam(value = "pages", defaultValue = "0") int pages) {
		logger.info("getShares(), keyword = {}, page = {}", keyword, pages);
		Slice<ShareResponseDto> shareResponseDto = shareService.getShares(keyword, pages);
		return ResponseEntity.ok().body(shareResponseDto);
	}

	@Operation(summary = "공유마당 게시글 삭제", description = "공유마당 게시글을 삭제합니다.")
	@DeleteMapping("/{articleId}")
	@CurrentUserId("userId")
	public ResponseEntity<Void> deleteShare(UserIdDto userId, HttpServletRequest request, @PathVariable Long articleId) {
		logger.info("deleteShare(), articleId = {}", articleId);
		shareService.deleteShare(userId, articleId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "공유마당 게시글 좋아요/싫어요, 작성자여부 조회", description = "공유마당 게시글에 좋아요/싫어요 및 작성자 여부를 조회합니다.")
	@GetMapping("/{articleId}/author-mark-info")
	@CurrentUserId("userId")
	public ResponseEntity<AuthorMarkInfoDto> getAuthorMarkInfo(UserIdDto userId, HttpServletRequest request, @PathVariable Long articleId) {
		logger.info("getAuthorMarkInfo(), articleId = {}", articleId);
		return ResponseEntity.ok().body(shareService.getAuthorMarkInfo(userId, articleId));
	}

	@Operation(summary = "공유마당 게시글 좋아요/싫어요", description = "공유마당 게시글에 좋아요/싫어요를 누릅니다.")
	@PutMapping("/{articleId}/marks")
	@CurrentUserId("userId")
	public ResponseEntity<Integer> markLikes(UserIdDto userId, HttpServletRequest request, @PathVariable Long articleId, @RequestBody ShareMarkRequestDto markRequestDto) {
		logger.info("markLikes(), articleId = {}", articleId);
		shareService.markLikes(userId, articleId, markRequestDto);
		return ResponseEntity.ok().body(markRequestDto.getMark());
	}

	@Operation(summary = "공유마당 게시글(견적) 마이페이지 저장", description = "공유마당 게시글을 마이페이지에 저장합니다.")
	@PostMapping("/{articleId}/quotes")
	@CurrentUserId("userId")
	public ResponseEntity<Long> saveShare(UserIdDto userId, HttpServletRequest request, @RequestBody ShareAddQuoteRequestDto shareAddQuoteRequestDto) {
		logger.info("saveShare()");
		// shareService.saveShare(userId, shareAddQuoteRequestDto);
		return ResponseEntity.ok().body(shareService.saveShare(userId, shareAddQuoteRequestDto));
	}

	@Operation(summary = "공유마당 좋아요가 가장 많은 상위 5개", description = "공유마당 좋아요가 가장 많은 상위 5개의 게시글을 조회합니다.")
	@GetMapping("/top5")
	public ResponseEntity<List<ShareDetailDto>> getTop5SharesWithMostLikes() {
		logger.info("getTop5SharesWithMostLikes()");
		return ResponseEntity.ok().body(shareService.getTop5SharesWithMostLikeCnt());
	}

	@DeleteMapping("/{quoteId}/quotes")
	@CurrentUserId("userId")
	public ResponseEntity<String> deleteShare(UserIdDto userId, HttpServletRequest request, @PathVariable long quoteId) {
		return ResponseEntity.ok().body(shareService.deleteShare(userId, quoteId));
	}
}
