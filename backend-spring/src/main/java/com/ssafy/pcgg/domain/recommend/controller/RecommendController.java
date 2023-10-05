package com.ssafy.pcgg.domain.recommend.controller;

import com.ssafy.pcgg.domain.recommend.dto.PartRequestDto;
import com.ssafy.pcgg.domain.recommend.dto.QuoteRequestDto;
import com.ssafy.pcgg.domain.recommend.dto.*;
import com.ssafy.pcgg.domain.recommend.service.RecommendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Recommends",description = "추천 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/recommends")
public class RecommendController {
    private final RecommendService recommendService;
    private final Logger logger = LoggerFactory.getLogger(RecommendService.class.getName());

    @Operation(summary = "PC견적 추천받기", description = "PC견적을 추천받습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/desktop")
    public ResponseEntity<?> createDesktopRecommend(@RequestBody QuoteRequestDto quoteRequestDto){
        try{
            List<QuoteResponseDto> responseDtoList = recommendService.createRecommend(quoteRequestDto);
            return ResponseEntity.ok().body(responseDtoList);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Laptop 추천받기", description = "Laptop을 추천받습니다.")
    @PostMapping("/laptop")
    public ResponseEntity<?> getLaptopRecommend(@RequestBody LaptopRequestDto laptopRequestDto){
        logger.info(laptopRequestDto.toString());
        try{
            List<LaptopResponseDto> laptopResponseList = recommendService.getLaptopRecommend(laptopRequestDto);
            return ResponseEntity.ok().body(laptopResponseList);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "부품 추천받기", description = "부품을 추천받습니다.")
    @PostMapping("/part")
    public ResponseEntity<?> getPartRecommend(@RequestBody PartRequestDto partRequestDto){
        logger.trace("부품추천 controller 진입");
        logger.info(partRequestDto.toString());
        try{
            List<?> partDtoList = recommendService.getPartRecommend(partRequestDto);
            return ResponseEntity.ok().body(partDtoList);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "추천 결과 상세조회", description = "선택한 추천결과를 조회합니다.")
    @GetMapping("/{category}/{resultNo}")
    public ResponseEntity<?> getRecommendDetail(@PathVariable String category, @PathVariable int resultNo){
        Map<String,Object> resultMap;
        HttpStatus httpStatus;
        try{
//            resultMap = recommendService.bussinessLogic();
            httpStatus = HttpStatus.OK;
        }catch(Exception e){
            resultMap = new HashMap<>();
            resultMap.put("message","unexpected ERROR");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
//        return new ResponseEntity<>(resultMap, httpStatus);
        //todo:추천결과 리턴형태에 따라 작성필요. 현재형태는 작성 X
        return null;
    }

    //추천결과 저장
    @Operation(summary = "견적추천결과를 저장", description = "선택한 추천결과를 마이페이지에 저장합니다.")
    @PostMapping
    public ResponseEntity<?> saveQuoteRecommend(@RequestBody SaveQuoteRequestDto saveQuoteRequestDto){
        //todo: 유저id값을 직접 받아오는게 맞는지?
        try{
            recommendService.saveQuoteRecommendToMyPage(saveQuoteRequestDto);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    //크롤링한 뒤 새로운 QuoteCandidate(견적후보) 생성
    @Operation(summary = "견적후보 생성", description = "CPU, GPU, RAM으로 견적후보를 생성합니다.")
    @PostMapping("/desktop/after-crawl")
    public ResponseEntity<?> classifyAndCreateCandidate(){
        Map<String,Object> resultMap = new HashMap<>();
        HttpStatus httpStatus;
        try{
            logger.trace("견적후보 생성 controller 진입");
            httpStatus = recommendService.classifyAndCreateCandidate();
            resultMap.put("message","요청 처리됨");
        }catch(Exception e){
            logger.error("unexpected ERROR : "+e.getStackTrace()+"/"+e.getCause()+"/"+e.getClass());
            resultMap.put("message","unexpected ERROR");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, httpStatus);
    }
}
