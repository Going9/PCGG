package com.ssafy.pcgg.domain.recommend.controller;

import com.ssafy.pcgg.domain.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/recommends")
public class RecommendController {
    private final RecommendService recommendService;

    //데스크탑추천
    @GetMapping("/desktop")
    public ResponseEntity<?> getDesktopRecommend(){
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
        return null;

    }

    //랩탑 추천
    @GetMapping("/laptop")
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
        return null;

    }

    //부품 추천
    @GetMapping("/part")
    public ResponseEntity<?> getPartRecommend(){
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
        return null;
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
        return null;
    }

    //저장한 추천 목록 조회
    @PutMapping("desktop")
    public ResponseEntity<?> classifyAndCreateCandidate(){
        Map<String,Object> resultMap = new HashMap<>();
        HttpStatus httpStatus;
        try{
            httpStatus = recommendService.classifyAndCreateCandidate();
            resultMap.put("message","");
        }catch(Exception e){
            resultMap.put("message","unexpected ERROR");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, httpStatus);
    }
}
