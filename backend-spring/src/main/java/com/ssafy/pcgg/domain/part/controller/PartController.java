package com.ssafy.pcgg.domain.part.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pcgg.domain.part.service.PartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Parts", description = "부품 API")
@RestController
@RequestMapping("/api/v1/parts")
@RequiredArgsConstructor
public class PartController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final PartService partService;

	/**
	 * PC부품 검색
	 * @param type
	 * @param keyword
	 * @return
	 */
	@Operation(summary = "PC부품 검색", description = "PC부품을 검색합니다.")
	@GetMapping("/{type}")
	public ResponseEntity<Slice<?>> searchParts(@PathVariable String type, @RequestParam(name = "q", required = false) String keyword, @RequestParam(value = "pages", defaultValue = "0") int pages) {
		if(keyword == null || keyword.isEmpty()) {
			logger.info("searchParts(), type = {}, pages = {}", type, pages);
			return ResponseEntity.ok().body(partService.getParts(type, pages));
		}
		logger.info("searchParts(), type = {}, keyword = {}, pages = {}", type, keyword, pages);
		return ResponseEntity.ok().body(partService.searchParts(type, pages, keyword));
	}

}
