package com.ssafy.pcgg.domain.recommend.util;

import com.ssafy.pcgg.domain.part.entity.*;
import com.ssafy.pcgg.domain.part.exception.NoSuchPartTypeException;
import com.ssafy.pcgg.domain.part.repository.*;
import com.ssafy.pcgg.domain.recommend.entity.*;
import com.ssafy.pcgg.domain.recommend.exception.QuoteCandidateException;
import com.ssafy.pcgg.domain.recommend.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RecommendUtil {
    //LOW - 저사양,보급형, MIDDLE - 평범, GOOD - 준수, HIGH - 고사양
    private final Integer LOW=1, MIDDLE=2, GOOD=3, HIGH=4;
    private final QuoteCandidateRepository quoteCandidateRepository;
    private final CpuRepository cpuRepository;
    private final RamRepository ramRepository;
    private final GpuRepository gpuRepository;
    private final PowerRepository powerRepository;
    private final PartTypeNsRepository partTypeNsRepository;
    private final Logger logger = LoggerFactory.getLogger(RecommendUtil.class.getName());
    private List<RamEntity>[] ramListCalced = new List[4];
    private List<GpuEntity>[] gpuListCalced = new List[4];
    private List<CpuEntity>[] cpuListCalced = new List[4];
//    private List<RamEntity>[] ramListCalced = new List[4];



    @SuppressWarnings("unchecked")
    @Transactional
    public void classifyPower(List<?> partList) {
        String grade;
        for(PowerEntity power : (List<PowerEntity>)partList){
            grade = power.getGrade();
            if (grade==null || grade.equals("")) continue;
            switch(grade){
                case "standard","bronze" -> power.setClassColumn(LOW);
                case "silver","gold" -> power.setClassColumn(MIDDLE);
                case "platinum","titanium" -> power.setClassColumn(HIGH);
                default -> logger.error("Power 분류 중 목록에 없는 grade 발생");
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public void classifyGpu(List<?> partList) {
        Integer performance;
        for(GpuEntity gpu : (List<GpuEntity>)partList) {
            performance = gpu.getScore();
            if(performance==null || performance==0) {}
            else if(performance<21000) gpu.setClassColumn(LOW);
            else if(performance<25000) gpu.setClassColumn(MIDDLE);
            else if(performance<30000) gpu.setClassColumn(GOOD);
            else gpu.setClassColumn(HIGH);
        }
        filterUnneecessaryGpu();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public void classifyRam(List<?> partList) {
        Integer capacity;
//        int readSpeed;
        for(RamEntity ram : (List<RamEntity>)partList){
            capacity = ram.getCapacity();
            if(capacity==null || capacity==0) {}
            else if(capacity==4) ram.setClassColumn(LOW);
            else if(capacity==8) ram.setClassColumn(MIDDLE);
            else if(capacity==16) ram.setClassColumn(GOOD);
            else if(capacity==32 || capacity==64) ram.setClassColumn(HIGH);
        }
        filterUnnecessaryRam();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public void classifyCpu(List<?> partList) {
        Integer performance;
        for(CpuEntity cpu : (List<CpuEntity>)partList){
            performance = cpu.getSingleScore();
            logger.trace("cpu 분류중. 현재 cpu의 singlescore :"+performance);
            logger.trace(cpu.getName()+" / class:"+cpu.getClassColumn()+" / Id:"+cpu.getId()+" / price:"+cpu.getPrice());
            if(performance==null || performance==0) {continue;}
            else if(performance<1600) cpu.setClassColumn(LOW);
            else if(performance<1800) cpu.setClassColumn(MIDDLE);
            else if(performance<2000) cpu.setClassColumn(GOOD);
            else if(performance>=2000) cpu.setClassColumn(HIGH);
            logger.trace("다음 클래스로 분류됨 : "+cpu.getClassColumn());
        }
        filterUnnecessaryCpu();
        logger.trace("classifyCpu 종료");
    }

    public void classifyMainboard(List<?> partList) {
        String grade;
        String pattern;
        for(MainboardEntity mainboard : (List<MainboardEntity>)partList){
            grade = mainboard.getGrade();
            if(grade==null) continue;
            logger.trace("mainboard의 grade :"+grade);
            if(Pattern.compile("^(H.1.|A.+)$").matcher(grade).matches()){
                mainboard.setClassColumn(LOW);
            } else if(Pattern.compile("^B.+$").matcher(grade).matches()){
                mainboard.setClassColumn(MIDDLE);
            } else if(Pattern.compile("^H.1.+$").matcher(grade).matches()){
                mainboard.setClassColumn(GOOD);
            } else if(Pattern.compile("^(Z.+|X.+)$").matcher(grade).matches()){
                mainboard.setClassColumn(HIGH);
            }
            logger.trace("다음 클래스로 분류됨 : "+mainboard.getClassColumn());
        }
    }

    private void filterUnnecessaryRam() {
        //필터링작업 - 윗용량의 제일 싼 물품보다 비싼 ram이 있다면 제거
        // ex) 128기가 램 중 제일 싼 것이 130만원이면 64기가 중 130만원보다 비싼 램은 제거
        List<RamEntity> ramList = ramRepository.findAllByClassColumnAndPriceAndCapacity();
        logger.trace("제거작업 전 사이즈 : "+String.valueOf(ramList.size()));
        int tmpCapacity = ramList.get(ramList.size()-1).getCapacity();
        int minPriceOfNextCapacity = ramList.get(ramList.size()-1).getPrice();
        for(int i=ramList.size()-1;i>=0;i--){
            if(ramList.get(i).getCapacity()<tmpCapacity) minPriceOfNextCapacity = ramList.get(i+1).getPrice();
            if(ramList.get(i).getPrice()>minPriceOfNextCapacity) ramList.remove(i);
        }
        logger.trace("제거작업 후 사이즈 : "+String.valueOf(ramList.size()));

        for(int r=PerformanceRequirement.LOW; r<=PerformanceRequirement.HIGH; r++) {
            ramList = ramRepository.findByClassColumnAndPriceAndCapacity(r);
            ramListCalced[r - 1] = ramList;
        }
    }
    private void filterUnnecessaryCpu() {
        List<CpuEntity> cpuList = cpuRepository.findAllByClassColumnAndPriceAndScore();
        logger.trace("제거작업 전 사이즈 : "+String.valueOf(cpuList.size()));
        int tmpClass = cpuList.get(cpuList.size()-1).getClassColumn();
        int minPriceOfNextClass = cpuList.get(cpuList.size()-1).getPrice();
        for(int i=cpuList.size()-1;i>=0;i--){
            if(cpuList.get(i).getClassColumn()<tmpClass) minPriceOfNextClass = cpuList.get(i+1).getPrice();
            if(cpuList.get(i).getPrice()>minPriceOfNextClass) cpuList.remove(i);
        }
        logger.trace("제거작업 후 사이즈 : "+String.valueOf(cpuList.size()));

        for(int r=PerformanceRequirement.LOW; r<=PerformanceRequirement.HIGH; r++) {
            cpuList = cpuRepository.findByClassColumnAndPriceAndSingleScore(r);
            cpuListCalced[r - 1] = cpuList;
        }
    }

    private void filterUnneecessaryGpu() {
        List<GpuEntity> gpuList = gpuRepository.findAllByClassColumnAndPriceAndScore();
        logger.trace("gpu제거작업 전 사이즈 : "+gpuList.size());
        int tmpClass = gpuList.get(gpuList.size()-1).getClassColumn();
        int minPriceOfNextClass = gpuList.get(gpuList.size()-1).getPrice();
        for(int i=gpuList.size()-1;i>=0;i--) {
            if (gpuList.get(i).getClassColumn() < tmpClass) minPriceOfNextClass = gpuList.get(i + 1).getPrice();
            if (gpuList.get(i).getPrice() > minPriceOfNextClass) gpuList.remove(i);
        }
        logger.trace("gpu제거작업 후 사이즈 : "+String.valueOf(gpuList.size()));

        for(int g=PerformanceRequirement.LOW; g<=PerformanceRequirement.HIGH; g++) {
            gpuList = gpuRepository.findByClassColumnAndPriceAndScore(g);
            gpuListCalced[g - 1] = gpuList;
        }
    }

    @Transactional
    public List<List<?>> makePartList(UsageNsEntity usage) {
        int c,r,g;

        switch(usage.getName()){
            case "가성비사무","저사양개발"
                    -> { c=LOW; r=LOW; g=LOW; /*p=LOW;*/ }
            case "고성능사무"
                    -> { c=MIDDLE; r=MIDDLE; g=LOW; /*p=MIDDLE;*/ }
            case "캐주얼게임"
                    -> { c=LOW; r=MIDDLE; g=LOW; /*p=LOW;*/ }
            case "중사양게임"
                    -> { c=MIDDLE; r=MIDDLE; g=MIDDLE; /*p=MIDDLE;*/ }
            case "고사양게임"
                    -> { c=HIGH; r=GOOD; g=HIGH; /*p=HIGH;*/ }
            case "일반영상편집"
                    -> { c=GOOD; r=MIDDLE; g=MIDDLE; /*p=LOW;*/ }
            case "전문영상편집"
                    -> { c=HIGH; r=HIGH; g=GOOD; /*p=MIDDLE;*/ }
            case "3D디자인"
                    -> { c=HIGH; r=HIGH; g=HIGH; /*p=HIGH;*/ }
            case "일반방송"
                    -> { c=GOOD; r=LOW; g=MIDDLE; /*p=MIDDLE;*/ }
            case "캐주얼게임방송"
                    -> { c=MIDDLE; r=MIDDLE; g=GOOD; /*p=MIDDLE;*/ }
            case "고성능게임방송"
                    -> { c=GOOD; r=HIGH; g=HIGH; /*p=HIGH;*/ }
            case "고사양개발"
                    -> { c=GOOD; r=HIGH; g=GOOD; /*p=MIDDLE;*/ }
            default -> throw new QuoteCandidateException("용도 매칭되지 않음. usage_ns의 레코드 점검필요");
        }

        List<List<?>> partList = new ArrayList<>();
        partList.add(cpuListCalced[c-1]);
        partList.add(ramListCalced[r-1]);
        partList.add(gpuListCalced[g-1]);
        return partList;
    }

    @Transactional
    public void generateScenario(UsageNsEntity usage, List<CpuEntity> cpuList, List<RamEntity> ramList, List<GpuEntity> gpuList) {
        logger.trace("generateScenario 메소드 진입");
        QuoteCandidateEntity tmpCandidate = new QuoteCandidateEntity();
        int count=0;

        for(CpuEntity cpu: cpuList){
            tmpCandidate.setCpu(cpu);
            logger.trace("cpuId:"+cpu.getId());
            for(RamEntity ram: ramList){
                if(checkAddable(tmpCandidate, ram)){
                    tmpCandidate.setRam(ram);
                    for(GpuEntity gpu: gpuList){
                        if (checkAddable(tmpCandidate, gpu)) {
                            tmpCandidate.setGpu(gpu);
                            QuoteCandidateEntity quoteCandidateEntity = QuoteCandidateEntity.builder()
                                    .usage(usage)
                                    .cpu(tmpCandidate.getCpu())
                                    .ram(tmpCandidate.getRam())
                                    .gpu(tmpCandidate.getGpu())
                                    .totalPrice(tmpCandidate.getCpu().getPrice()
                                        +tmpCandidate.getRam().getPrice()
                                        +tmpCandidate.getGpu().getPrice()
                                    )
                                    .benchScore(tmpCandidate.getCpu().getSingleScore()
                                        +tmpCandidate.getRam().getMemoryClock()
                                        +tmpCandidate.getGpu().getScore()
                                    )
                                    .build();
                            quoteCandidateRepository.save(quoteCandidateEntity);
                            count++;
                        }
                    }
                } //else continue
            }
        }
        logger.info(count+"건 생성");
    }

    private boolean checkAddable(QuoteCandidateEntity tmpCandidate, RamEntity ram) {
        //CPU와 호환여부 체크
        String ramSpec = ram.getMemorySpec();
        CpuEntity cpuEntity = tmpCandidate.getCpu();
        if(Objects.equals(ramSpec, "DDR4")){
            return cpuEntity.getDdr4();
        }else if(Objects.equals(ramSpec, "DDR5")){
            return cpuEntity.getDdr5();
        }else throw new QuoteCandidateException("부품 정보에 이상있음.");
    }
    private boolean checkAddable(QuoteCandidateEntity tmpCandidate, GpuEntity gpu) {
        //https://m-sooriya.tistory.com/944
        //CPU <> GPU의 병목현상 정도를 계산해서 최악일 경우 걸러줄 수 있겠지만 cpu가 최악이 아닌 이상 유의미한 차이 없음
        //차후 추천로직 심화 시 반영 가능
        return true;
    }

    public int getClassByUsageAndPartType(String usage, String partType) throws QuoteCandidateException{
        //용도에 따른 부품의 별 성능수준(Class)을 반환하는 Util 메소드
        int result;
        return result = switch (partType) {
            //읽는법: cpu의 용도가 "가성비사무"면 요구하는 성능수준은 LOW(1)
            case "cpu" -> switch (usage) {
                case "가성비사무", "저사양개발", "캐주얼게임" -> PerformanceRequirement.LOW;
                case "고성능사무", "중사양게임" -> PerformanceRequirement.MIDDLE;
                case "일반영상편집", "일반방송", "캐주얼게임방송", "고사양개발" -> PerformanceRequirement.GOOD;
                case "고사양게임", "전문영상편집", "3D디자인", "고성능게임방송" -> PerformanceRequirement.HIGH;
                default -> throw new QuoteCandidateException("잘못된 usage In cpu. check " + usage);
            };
            case "mainboard" -> switch (usage) {
                case "가성비사무", "저사양개발", "캐주얼게임" -> PerformanceRequirement.LOW;
                case "고성능사무", "중사양게임", "일반영상편집", "일반방송", "캐주얼게임방송" -> PerformanceRequirement.MIDDLE;
                case "고사양개발" -> PerformanceRequirement.GOOD;
                case "고사양게임", "전문영상편집", "3D디자인", "고성능게임방송" -> PerformanceRequirement.HIGH;
                default -> throw new QuoteCandidateException("잘못된 usage In mainboard. check " + usage);
            };
            case "ram" -> switch (usage) {
                case "가성비사무", "저사양개발", "일반방송" -> PerformanceRequirement.LOW;
                case "고성능사무", "캐주얼게임", "중사양게임", "일반영상편집", "캐주얼게임방송" -> PerformanceRequirement.MIDDLE;
                case "고사양게임" -> PerformanceRequirement.GOOD;
                case "전문영상편집", "3D디자인", "고성능게임방송", "고사양개발" -> PerformanceRequirement.HIGH;
                default -> throw new QuoteCandidateException("잘못된 usage In ram. check " + usage);
            };
            case "gpu" -> switch (usage) {
                case "가성비사무", "저사양개발", "고성능사무", "캐주얼게임" -> PerformanceRequirement.LOW;
                case "중사양게임", "일반영상편집" -> PerformanceRequirement.MIDDLE;
                case "고사양게임", "3D디자인", "고성능게임방송", "고사양개발" -> PerformanceRequirement.HIGH;
                case "전문영상편집", "일반방송", "캐주얼게임방송" -> PerformanceRequirement.GOOD;
                default -> throw new QuoteCandidateException("잘못된 usage In gpu. check " + usage);
            };
            case "power" -> switch (usage) {
                case "가성비사무", "저사양개발", "캐주얼게임", "일반영상편집" -> PerformanceRequirement.LOW;
                case "고성능사무", "중사양게임", "전문영상편집", "일반방송", "캐주얼게임방송", "고사양개발" -> PerformanceRequirement.MIDDLE;
                case "고사양게임", "3D디자인", "고성능게임방송" -> PerformanceRequirement.HIGH;
                default -> throw new QuoteCandidateException("잘못된 usage In power. check " + usage);
            };
            default -> throw new NoSuchPartTypeException();
        };
    }
}