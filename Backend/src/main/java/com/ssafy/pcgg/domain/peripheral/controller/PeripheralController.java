package com.ssafy.pcgg.domain.peripheral.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pcgg.domain.peripheral.dto.PeripheralResponseDto;
import com.ssafy.pcgg.domain.peripheral.service.PeripheralService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Peripherals", description = "주변기기 API")
@RestController
@RequestMapping("/api/v1/peripherals")
@RequiredArgsConstructor
public class PeripheralController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final PeripheralService peripheralService;

	@Operation(summary = "주변기기 목록 조회", description = "카테고리별, 주변기기 목록을 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Slice.class))),
		@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
		@ApiResponse(responseCode = "404", description = "NOT FOUND"),
		@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
	})
	@GetMapping("/{category}")
	public ResponseEntity<Slice<PeripheralResponseDto>> keyBoardList(@PathVariable String category, @RequestParam(value = "pages", defaultValue = "0") int pages) {
		logger.debug("keyBoardList(), category = {}, pages = {}", category, pages);
		Slice<PeripheralResponseDto> peripheralResponseDtoSlice = peripheralService.peripheralList(category, pages);
		return ResponseEntity.ok().body(peripheralResponseDtoSlice);
	}

}