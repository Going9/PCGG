package com.ssafy.pcgg.domain.recommend.service;

import com.ssafy.pcgg.domain.recommend.exception.ClassifyPartException;
import com.ssafy.pcgg.domain.recommend.exception.QuoteCandidateException;
import com.ssafy.pcgg.domain.recommend.repository.*;
import com.ssafy.pcgg.domain.recommend.util.RecommendUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService {
    //LOW - 저사양,보급형, MIDDLE - 평범, GOOD - 준수, HIGH - 고사양
    private final Integer LOW=1, MIDDLE=2, GOOD=3, HIGH=4;
    private final RecommendUtil recommendUtil;
    private final Logger logger;

    private final QuoteCandidateRepository quoteCandidateRepository;
    private final UsageNsRepository usageNsRepository;
    //    private final QuoteRepository quoteRepository;
    private final CpuRepository cpuRepository;
    private final RamRepository ramRepository;
    private final GpuRepository gpuRepository;
    private final PowerRepository powerRepository;

    public HttpStatus classifyAndCreateCandidate() {
        try{
            classifyPart();
        } catch(ClassifyPartException e){
//            logger.error("message","부품 분류 중 오류 발생");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try{
//            recommendUtil.deleteAndCreateQuoteCandidate();
        } catch(QuoteCandidateException e){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.OK;
    }

    public void classifyPart() {
        List<?> partList;
        try {
            partList = cpuRepository.findAllByClass(null);
            recommendUtil.classifyCpu(partList);
        } catch(ClassifyPartException e){
            logger.error("cpu 분류 중 에러 발생", e);
        }

        try{
            partList = ramRepository.findAllByClass(null);
            recommendUtil.classifyRam(partList);
        } catch(ClassifyPartException e){
            logger.error("ram 분류 중 에러 발생", e);
        }
        try{
            partList = gpuRepository.findAllByClass(null);
            recommendUtil.classifyGpu(partList);
        } catch(ClassifyPartException e){
            logger.error("gpu 분류 중 에러 발생", e);
        }
        try{
            partList = powerRepository.findAllByClass(null);
            recommendUtil.classifyPower(partList);
        } catch(ClassifyPartException e){
            logger.error("cpu 분류 중 에러 발생", e);
        }
    }
}
