package com.ssafy.pcgg.domain.recommend.util;

import com.ssafy.pcgg.domain.recommend.entity.*;
import com.ssafy.pcgg.domain.recommend.exception.ClassifyPartException;
import com.ssafy.pcgg.domain.recommend.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RecommendUtil {
    //LOW - 저사양,보급형, MIDDLE - 평범, GOOD - 준수, HIGH - 고사양
    private final Integer LOW=1, MIDDLE=2, GOOD=3, HIGH=4;
    private final QuoteCandidateRepository quoteCandidateRepository;
    private final UsageNsRepository usageNsRepository;

    private final Logger logger;

    @Transactional
    public void classifyPower(List<?> partList) {
        for(PowerEntity power : (List<PowerEntity>)partList){
            switch(power.getGrade()){
                case "standard","bronze" -> power.setClassColumn(LOW);
                case "silver","gold" -> power.setClassColumn(MIDDLE);
                case "platinum","titanium" -> power.setClassColumn(HIGH);
                default -> logger.error("Power 분류 중 목록에 없는 grade 발생");
            }
        }
    }

    public void classifyGpu(List<?> partList) {
        int performance;
        for(GpuEntity gpu : (List<GpuEntity>)partList) {
            //gpu 크롤링 결과 나오면 컬럼 추가 및 세부수치 조정
            performance = 100;//gpu.getPerformance();
            if(performance<1000) gpu.setClassColumn(LOW);
            else if(performance<2000) gpu.setClassColumn(MIDDLE);
            else if(performance<3000) gpu.setClassColumn(GOOD);
            else if(performance<4000) gpu.setClassColumn(HIGH);
        }
    }

    public void classifyRam(List<?> partList) {
        int capacity;
        int readSpeed;
        for(RamEntity ram : (List<RamEntity>)partList){
            //Ram 크롤링 결과 나오면 컬럼 추가 및 세부수치 조정
            capacity = 8;//ram.getCapacity();
            if(capacity==4) ram.setClassColumn(LOW);
            else if(capacity==8) ram.setClassColumn(MIDDLE);
            else if(capacity==16) ram.setClassColumn(GOOD);
            else if(capacity==32 || capacity==64) ram.setClassColumn(HIGH);
        }
    }

    public void classifyCpu(List<?> partList) {
        int performance;
        for(CpuEntity cpu : (List<CpuEntity>)partList){
            //Ram 크롤링 결과 나오면 컬럼 추가 및 세부수치 조정
            performance = 8; cpu.getSingleScore();
            if(performance<1600) cpu.setClassColumn(LOW);
            else if(performance<1800) cpu.setClassColumn(MIDDLE);
            else if(performance<2000) cpu.setClassColumn(GOOD);
            else if(performance>=2000) cpu.setClassColumn(HIGH);
        }
    }

//    @Transactional
//    public HttpStatus deleteAndCreateQuoteCandidate() {
//        //생성 전 기존 리스트 제거
//        quoteCandidateRepository.deleteAll();
//
//        //견적용도별로 새로운 리스트 산출
//        List<QuoteCandidateEntity> quoteList = new ArrayList<>();
//        List<UsageNsEntity> usageList = usageNsRepository.findAll();
//
//        for(UsageNsEntity usage : usageList){
//            QuoteCandidateEntity quoteCandidate = new QuoteCandidateEntity();
//            switch(usage.getName()){
//                case "가성비사무" -> {
//                    pickCpu(LOW);
//                    pickRam(MIDDLE);
//                    pickGpu(LOW);
//                    pickPower();
//                    pickMainboard(MIDDLE);
//                }
//                case "고성능사무" -> {
//                }
//                case "캐주얼게임" -> {
//                }
//                case "중사양게임" -> {}
//                case "고사양게임" -> {}
//                case "3d디자인" -> {}
//                case "영상편집" -> {}
//                case "일반방송" -> {}
//                case "캐주얼게임방송" -> {}
//                case "고성능게임방송" -> {}
//                case "저사양개발" -> {
//
//                }
//                case "중사양개발" -> {
//
//                }
//                case "고사양개발" -> {
//
//                }
//                default -> resultMap.put("message","Failed while making 견적후보");
//            }
//            quoteCandidate.setUsage(usage.getName());
//            quoteList.add(quoteCandidate);
//        }
//        quoteCandidateRepository.saveAll(quoteList);
//        return resultMap;
//    }
}
