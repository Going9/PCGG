package com.ssafy.pcgg.domain.recommend.controller;

import com.ssafy.pcgg.domain.recommend.dto.PartDto;
import com.ssafy.pcgg.domain.recommend.dto.PartRequestDto;
import com.ssafy.pcgg.domain.recommend.dto.QuoteRequestDto;
import com.ssafy.pcgg.domain.recommend.service.RecommendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

    //데스크탑추천
    @Operation(summary = "PC견적 추천받기", description = "PC견적을 추천받습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("/desktop")
    public ResponseEntity<?> createDesktopRecommend(@RequestBody QuoteRequestDto quoteRequestDto){
        Map<String,Object> resultMap = new HashMap<>();
        HttpStatus httpStatus;
        try{
            resultMap.put("resultList",recommendService.createRecommend(quoteRequestDto));
            httpStatus = HttpStatus.OK;
        }catch(Exception e){
            resultMap.put("message","unexpected ERROR");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, httpStatus);

    }

    //랩탑 추천
    @Operation(summary = "(미완성)Laptop 추천받기", description = "Laptop을 추천받습니다.")
    @PostMapping("/laptop")
    public ResponseEntity<?> getLaptopRecommend(){
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
        //todo:미완성
        return null;
    }

    //부품 추천
    @Operation(summary = "부품 추천받기", description = "부품을 추천받습니다.")
    @PostMapping("/part")
    public ResponseEntity<?> getPartRecommend(@RequestBody PartRequestDto partRequestDto){
        System.out.println("it's in");
        logger.info(partRequestDto.toString());
        try{
            List<PartDto> partDtoList = (List<PartDto>) recommendService.getPartRecommend(partRequestDto);
            return ResponseEntity.ok().body(partDtoList);
        }catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    //추천결과 상세조회
    @Operation(summary = "(미완성)추천 결과 상세조회", description = "선택한 추천결과를 조회합니다.")
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
        //todo:미완성
        return null;
    }

    //추천결과 저장
    @Operation(summary = "(미완성)추천결과를 저장", description = "선택한 추천결과를 마이페이지에 저장합니다.")
    @PostMapping
    public ResponseEntity<?> saveRecommend(){
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
        //todo:미완성
        return null;
    }

    //크롤링한 뒤 새로운 QuoteCandidate(견적후보) 생성
    @Operation(summary = "견적후보 생성", description = "CPU, GPU, RAM으로 견적후보를 생성합니다.")
    @PostMapping("/desktop/after-crawl")
    public ResponseEntity<?> classifyAndCreateCandidate(){
        Map<String,Object> resultMap = new HashMap<>();
        HttpStatus httpStatus;
        try{
            httpStatus = recommendService.classifyAndCreateCandidate();
            resultMap.put("message","요청 처리됨");
        }catch(Exception e){
            resultMap.put("message","unexpected ERROR");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, httpStatus);
    }
}
