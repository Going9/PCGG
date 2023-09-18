package com.ssafy.pcgg.domain.recommend.service;

import com.ssafy.pcgg.domain.recommend.entity.QuoteCandidateEntity;
import com.ssafy.pcgg.domain.recommend.entity.UsageNsEntity;
import com.ssafy.pcgg.domain.recommend.repository.QuoteCandidateRepository;
import com.ssafy.pcgg.domain.recommend.repository.QuoteRepository;
import com.ssafy.pcgg.domain.recommend.repository.UsageNsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final Integer LOW=0, MIDDLE=1, HIGH=2; //LOW - 보급형, MIDDLE - 준수, HIGH - 고사양
    private final QuoteCandidateRepository quoteCandidateRepository;
    private final UsageNsRepository usageNsRepository;
//    private final QuoteRepository quoteRepository;

    @Transactional
    public Map<String, Object> deleteAndCreateQuoteCandidate() {
        Map<String, Object> resultMap = new HashMap<>();

        //생성 전 기존 리스트 제거
        quoteCandidateRepository.deleteAll();

        //견적용도별로 새로운 리스트 산출
        List<QuoteCandidateEntity> quoteList = new ArrayList<>();
        List<UsageNsEntity> usageList = usageNsRepository.findAll();

        for(UsageNsEntity usage : usageList){
            QuoteCandidateEntity quoteCandidate = new QuoteCandidateEntity();
            switch(usage.getName()){
                case "가성비사무" -> {
                    recommendUtil.pickCpu(LOW);
                    recommendUtil.pickMainboard(MIDDLE);
                    recommendUtil.pickRam(MIDDLE);
                    recommendUtil.pickGpu(LOW);
                    recommendUtil.pickPower();

                    recommendUtil.pickSsd();
                    recommendUtil.pickChassis();
                    recommendUtil.pickCooler();
                }
                case "고성능사무" -> {
                }
                case "캐주얼게임" -> {
                }
                case "중사양게임" -> {}
                case "고사양게임" -> {}
                case "3d디자인" -> {}
                case "영상편집" -> {}
                case "일반방송" -> {}
                case "캐주얼게임방송" -> {}
                case "고성능게임방송" -> {}
                case "저사양개발" -> {

                }
                case "중사양개발" -> {

                }
                case "고사양개발" -> {

                }
                default -> resultMap.put("message","Failed while making 견적후보");
            }
            quoteCandidate.setUsage(usage.getName());
            quoteList.add(quoteCandidate);
        }
        quoteCandidateRepository.saveAll(quoteList);
        return resultMap;
    }
}
