package com.ssafy.pcgg.domain.recommend.service;

import com.ssafy.pcgg.domain.recommend.entity.*;
import com.ssafy.pcgg.domain.recommend.exception.ClassifyPartAllFailedException;
import com.ssafy.pcgg.domain.recommend.exception.ClassifyPartException;
import com.ssafy.pcgg.domain.recommend.exception.QuoteCandidateException;
import com.ssafy.pcgg.domain.recommend.repository.*;
import com.ssafy.pcgg.domain.recommend.util.RecommendUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RecommendUtil recommendUtil;
    private final Logger logger = LoggerFactory.getLogger(RecommendService.class.getName());

    private final QuoteCandidateRepository quoteCandidateRepository;
    private final UsageNsRepository usageNsRepository;
    //    private final QuoteRepository quoteRepository;
    private final CpuRepository cpuRepository;
    private final RamRepository ramRepository;
    private final GpuRepository gpuRepository;
    private final PowerRepository powerRepository;

    public HttpStatus classifyAndCreateCandidate() {
        //분류
        try{
            classifyPart();
        } catch(ClassifyPartException e){
            logger.error("부품 분류 중 오류 발생",e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        //QuoteCandidate 삭제 후 생성
        try{
            deleteAndCreateQuoteCandidate();
        } catch(QuoteCandidateException e){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.OK;
    }

    public void classifyPart() throws ClassifyPartException{
        List<?> partList;
        int exceptionCount = 0;

        //exception이 발생해도 다른 부품 분류 계속 진행
        try {
            partList = cpuRepository.findAllByClass(null);
            recommendUtil.classifyCpu(partList);
        } catch(ClassifyPartException e){
            logger.error("cpu 분류 중 에러 발생", e);
            exceptionCount++;
        }

        try{
            partList = ramRepository.findAllByClass(null);
            recommendUtil.classifyRam(partList);
        } catch(ClassifyPartException e){
            logger.error("ram 분류 중 에러 발생", e);
            exceptionCount++;
        }
        try{
            partList = gpuRepository.findAllByClass(null);
            recommendUtil.classifyGpu(partList);
        } catch(ClassifyPartException e){
            logger.error("gpu 분류 중 에러 발생", e);
            exceptionCount++;
        }
        try{
            partList = powerRepository.findAllByClass(null);
            recommendUtil.classifyPower(partList);
        } catch(ClassifyPartException e){
            logger.error("cpu 분류 중 에러 발생", e);
            exceptionCount++;
        }

        if(exceptionCount==4) throw new ClassifyPartAllFailedException();
    }

    @Transactional
    public void deleteAndCreateQuoteCandidate() {
//        final int CPU=0, RAM=1, GPU=2, POWER=3;
//        final int[] partIndex = {CPU, RAM, GPU, POWER};
        //생성 전 기존 리스트 제거
        //deleteCandidate

        //견적용도별로 새로운 리스트 산출
        //createCandidatea
        List<UsageNsEntity> usageList = usageNsRepository.findAll();
        for(UsageNsEntity usage : usageList){
            //용도별로 삭제 후 다시 insert
            quoteCandidateRepository.deleteByUsage(usage);
            //부품별 미분류된 리스트 추출
            List<List<?>> partList;
            try{
                partList = recommendUtil.makePartList(usage);
            } catch(QuoteCandidateException e){
                logger.error("용도 매칭되지 않음. usage_ns의 레코드 점검필요");
                continue; //다음 용도로 continue
            }

            //경우의 수 만들고 저장
            //noinspection unchecked
            recommendUtil.generateScenario(
                    usage
                    , (List<CpuEntity>) partList.get(0)
                    , (List<RamEntity>) partList.get(1)
                    , (List<GpuEntity>) partList.get(2));
        }
    }
}
