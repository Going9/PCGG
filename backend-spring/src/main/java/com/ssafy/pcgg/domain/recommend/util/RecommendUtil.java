package com.ssafy.pcgg.domain.recommend.util;

import com.ssafy.pcgg.domain.recommend.entity.*;
import com.ssafy.pcgg.domain.recommend.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final CpuRepository cpuRepository;
    private final RamRepository ramRepository;
    private final GpuRepository gpuRepository;
    private final PowerRepository powerRepository;
    private final Logger logger = LoggerFactory.getLogger(RecommendUtil.class.getName());

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

    @Transactional
    public void classifyGpu(List<?> partList) {
        Integer performance;
        for(GpuEntity gpu : (List<GpuEntity>)partList) {
            //gpu 크롤링 결과 나오면 컬럼 추가 및 세부수치 조정
            performance = 100;//gpu.getPerformance();
            if(performance<1000) gpu.setClassColumn(LOW);
            else if(performance<2000) gpu.setClassColumn(MIDDLE);
            else if(performance<3000) gpu.setClassColumn(GOOD);
            else if(performance<4000) gpu.setClassColumn(HIGH);
            else if(performance==null) logger.error(gpu.getName()+"의 performance 값 없음");
        }
    }

    @Transactional
    public void classifyRam(List<?> partList) {
        int capacity;
//        int readSpeed;
        for(RamEntity ram : (List<RamEntity>)partList){
            //Ram 크롤링 결과 나오면 컬럼 추가 및 세부수치 조정
            capacity = 8;//ram.getCapacity();
            if(capacity==4) ram.setClassColumn(LOW);
            else if(capacity==8) ram.setClassColumn(MIDDLE);
            else if(capacity==16) ram.setClassColumn(GOOD);
            else if(capacity==32 || capacity==64) ram.setClassColumn(HIGH);
        }
    }

    @Transactional
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

    public List<List<?>> makePartList(UsageNsEntity usage) {
        List<CpuEntity> cpuList;
        List<RamEntity> ramList;
        List<GpuEntity> gpuList;
        List<PowerEntity> powerList;
        int c,r,g,p;

        switch(usage.getName()){
            case "가성비사무","저사양개발"
                    -> { c=LOW; r=LOW; g=LOW; p=LOW; }
            case "고성능사무"
                    -> { c=MIDDLE; r=MIDDLE; g=LOW; p=MIDDLE; }
            case "캐주얼게임"
                    -> { c=LOW; r=MIDDLE; g=LOW; p=LOW; }
            case "중사양게임"
                    -> { c=MIDDLE; r=MIDDLE; g=MIDDLE; p=MIDDLE; }
            case "고사양게임"
                    -> { c=HIGH; r=GOOD; g=HIGH; p=HIGH; }
            case "일반영상편집"
                    -> { c=GOOD; r=MIDDLE; g=MIDDLE; p=LOW; }
            case "전문영상편집"
                    -> { c=HIGH; r=HIGH; g=GOOD; p=MIDDLE; }
            case "3d디자인"
                    -> { c=HIGH; r=HIGH; g=HIGH; p=HIGH; }
            case "일반방송"
                    -> { c=GOOD; r=LOW; g=MIDDLE; p=MIDDLE; }
            case "캐주얼게임방송"
                    -> { c=MIDDLE; r=MIDDLE; g=GOOD; p=MIDDLE; }
            case "고성능게임방송"
                    -> { c=GOOD; r=HIGH; g=HIGH; p=HIGH; }
            case "고사양개발"
                    -> { c=GOOD; r=HIGH; g=GOOD; p=MIDDLE; }
            default -> {
                logger.error("용도 매칭되지 않음. usage_ns의 레코드 점검필요");
                return null;
            }
        }
        cpuList = pickCpu(c);
        ramList = pickRam(r);
        gpuList = pickGpu(g);
        powerList = pickPower(p);
        List<List<?>> partList = new ArrayList<>();

        partList.add(cpuList);
        partList.add(ramList);
        partList.add(gpuList);
        partList.add(powerList);
        return partList;
    }

    @Transactional
    public void generateScenario(List<CpuEntity> cpuList, List<RamEntity> ramList, List<GpuEntity> gpuList, List<PowerEntity> powerList) {
        //만약 list들의 크기가 너무 크다면 pick단계에서 적절히 조절해야한다.
        QuoteCandidateEntity tmpCandidate = new QuoteCandidateEntity();

        for(CpuEntity cpu: cpuList){
            tmpCandidate.setCpu(cpu);
            for(RamEntity ram: ramList){
                if(checkAddable(tmpCandidate, ram)){
                    tmpCandidate.setRam(ram);
                } //else continue
                for(GpuEntity gpu: gpuList){
                    if (checkAddable(tmpCandidate, gpu)) {
                        tmpCandidate.setGpu(gpu);
                    }
                    for(PowerEntity power: powerList){
                        if(checkAddable(tmpCandidate, power)){
                            tmpCandidate.setPower(power);
                            QuoteCandidateEntity quoteCandidateEntity = QuoteCandidateEntity.builder()
                                    .cpu(tmpCandidate.getCpu())
                                    .ram(tmpCandidate.getRam())
                                    .gpu(tmpCandidate.getGpu())
                                    .power(tmpCandidate.getPower())
                                    .build();
                            quoteCandidateRepository.save(quoteCandidateEntity);
                        }
                    }
                }
            }
        }
    }

    private boolean checkAddable(QuoteCandidateEntity tmpCandidate, RamEntity ram) {
        //CPU와 호환여부 체크
        return false;
    }
    private boolean checkAddable(QuoteCandidateEntity tmpCandidate, GpuEntity gpu) {
        //CPU,RAM과 호환여부 체크
        return false;
    }
    private boolean checkAddable(QuoteCandidateEntity tmpCandidate, PowerEntity power) {
        //CPU,RAM,GPU와 호환여부 체크
        return false;
    }

    public List<PowerEntity> pickPower(Integer classColumn) {
        return powerRepository.findAllByClass(classColumn);
    }

    public List<GpuEntity> pickGpu(Integer classColumn) {
        return gpuRepository.findAllByClass(classColumn);
    }

    public List<RamEntity> pickRam(Integer classColumn) {
        return ramRepository.findAllByClass(classColumn);
    }

    public List<CpuEntity> pickCpu(Integer classColumn) {
        return cpuRepository.findAllByClass(classColumn);
    }


}
