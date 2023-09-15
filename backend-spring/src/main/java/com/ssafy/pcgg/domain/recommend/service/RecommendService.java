package com.ssafy.pcgg.domain.recommend.service;

import com.ssafy.pcgg.domain.recommend.dto.DesktopRecommendDto;
import com.ssafy.pcgg.domain.recommend.entity.QuoteCandidateEntity;
import com.ssafy.pcgg.domain.recommend.repository.QuoteCandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class RecommendService {
    private QuoteCandidateRepository quoteCandidateRepository;

    public Map<String, Object> desktopRecommend(DesktopRecommendDto desktopRecommendDto) {
        Map<String, Object> resultMap = new HashMap<>();
        /*
        사후작업(유저의 기능 호출 시 진행)
        1.1. 입력한 파라미터(우선순위-가격/성능/가성비, 용도 - 사무/게임/...)에 따라서 후보군 한정
            → 전체 데이터의 30~50% 수준으로 한정
        1.1.1 용도로 그룹을 필터링, 우선순위에 따라 다시 필터링
            → 10건 내외로 데이터 한정 or 최종건인 1건 추천(상의 필요)
        1.1.2 타 유저의 평가데이터 반영하여 3건 or 1건 추천
            필요사항
            1)후보군 세트 별 타 유저가 평가한 더미데이터 필요
            2)입력받은 용도에 따라 가중치 부여??(구체화 필요)
         */

        return resultMap;
    }

    //추천결과 상세조회
    public Map<String, Object> quoteDetail(String category, Integer quoteId) {
        Map<String, Object> resultMap = new HashMap<>();

        QuoteCandidateEntity quote = quoteCandidateRepository.findById(quoteId).orElse(null);
        resultMap.put("data",quote);
//        quote.getCpu();
//        quote.getMainboard();
//        quote.getSsd();
//        quote.getRam();
//        quote.getGpu();
//        quote.getChassis();
//        quote.getPower();
//        quote.getCooler();
//        quote.getBenchScore();
//        quote.getTotalPrice();

        return resultMap;
    }
}
