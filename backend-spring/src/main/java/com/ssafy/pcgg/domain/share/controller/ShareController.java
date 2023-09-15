package com.ssafy.pcgg.domain.share.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pcgg.domain.peripheral.dto.PeripheralResponseDto;
import com.ssafy.pcgg.domain.share.dto.SearchPartResponseDto;
import com.ssafy.pcgg.domain.share.dto.ShareAddRequestDto;
import com.ssafy.pcgg.domain.share.service.ShareService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

	/* 연결 테스트를 위한 부분으로 추후에 삭제 예정 */
	@GetMapping("/")
	public String testC() {
		return "connected!";
	}

	@Operation(summary = "공유마당 게시글 작성", description = "공유마당 게시글을 작성합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
		@ApiResponse(responseCode = "404", description = "NOT FOUND"),
		@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
	})
	@PostMapping("/")
	public ResponseEntity<?> writeShare(@RequestBody ShareAddRequestDto addRequestDto) {
		logger.info("writeShare(), userId = {}", addRequestDto.getUserId());

		Long quoteId = shareService.writeShare(addRequestDto);

		return ResponseEntity.ok().body(quoteId);
	}

}
