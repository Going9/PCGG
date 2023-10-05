package com.ssafy.pcgg.domain.simulation.controller;

import com.ssafy.pcgg.domain.simulation.dto.PartCheckRequestDto;
import com.ssafy.pcgg.domain.simulation.dto.PartCheckResponseDto;
import com.ssafy.pcgg.domain.simulation.service.SimulationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.pcgg.domain.part.service.PartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Tag(name = "Simulations", description = "시뮬레이션 API")
@RestController
@RequestMapping("/api/v1/simulations")
@RequiredArgsConstructor
public class SimulationController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SimulationService simulationService;

    /**
     * 부품호환성 검사
     * @param partCheckRequestDto
     */
    @Operation(summary = "부품호환성 체크", description = "현재 선택된 부품들의 호환성을 check합니다.")
    @PostMapping
    public ResponseEntity<?> checkCompatibility(@RequestBody PartCheckRequestDto partCheckRequestDto) {
        try{
            List<PartCheckResponseDto> responseDtoList = simulationService.checkCompatibility(partCheckRequestDto);
            return ResponseEntity.ok().body(responseDtoList);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}