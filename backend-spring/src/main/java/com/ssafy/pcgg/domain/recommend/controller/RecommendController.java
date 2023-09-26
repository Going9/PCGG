package com.ssafy.pcgg.domain.recommend.controller;

import com.ssafy.pcgg.domain.recommend.dto.CpuResponseDto;
import com.ssafy.pcgg.domain.recommend.dto.PartDto;
import com.ssafy.pcgg.domain.recommend.dto.PartRequestDto;
import com.ssafy.pcgg.domain.recommend.dto.QuoteRequestDto;
import com.ssafy.pcgg.domain.recommend.service.RecommendService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/recommends")
public class RecommendController {
    private final RecommendService recommendService;

    //데스크탑추천
    @GetMapping("/desktop")
    public ResponseEntity<?> getDesktopRecommend(@RequestBody QuoteRequestDto quoteRequestDto){
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
    @GetMapping("/laptop")
    public ResponseEntity<?> getLaptopRecommend(){
        HttpStatus httpStatus;
        try{
//            resultMap = recommendService.bussinessLogic();
            httpStatus = HttpStatus.OK;
        }catch(Exception e){
            resultMap.put("message","unexpected ERROR");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
//        return new ResponseEntity<>(resultMap, httpStatus);
        //todo:미완성
        return null;
    }

    //부품 추천
    @GetMapping("/part")
    public ResponseEntity<?> getPartRecommend(@RequestBody PartRequestDto partRequestDto){
        List<PartDto> partDtoList = null;
        HttpStatus httpStatus;
        try{
//            resultMap = recommendService.bussinessLogic();
            httpStatus = HttpStatus.OK;
        }catch(Exception e){
            resultMap = new HashMap<>();
            resultMap.put("message","unexpected ERROR");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(partDtoList, httpStatus);
    }

    //추천결과 상세ㅗㅈ회
    @GetMapping("{category}/{resultNo}")
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

    //저장한 추천 목록 조회
    @GetMapping("{userId}")
    public ResponseEntity<?> getSavedRecommendList(@PathVariable int userId){
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
    @PutMapping("desktop")
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
