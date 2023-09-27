package com.ssafy.pcgg.domain.recommend.service;

import com.ssafy.pcgg.domain.recommend.dto.*;
import com.ssafy.pcgg.domain.recommend.entity.*;
import com.ssafy.pcgg.domain.recommend.exception.ClassifyPartAllFailedException;
import com.ssafy.pcgg.domain.recommend.exception.ClassifyPartException;
import com.ssafy.pcgg.domain.recommend.exception.QuoteCandidateException;
import com.ssafy.pcgg.domain.recommend.repository.*;
import com.ssafy.pcgg.domain.recommend.util.PerformanceRequirement;
import com.ssafy.pcgg.domain.recommend.util.PrioritySelector;
import com.ssafy.pcgg.domain.recommend.util.RecommendUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private final SsdRepository ssdRepository;
    private final MainboardRepository mainboardRepository;
    private final ChassisRepository chassisRepository;


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
            partList = cpuRepository.findAllByClassColumn(null);
            recommendUtil.classifyCpu(partList);
        } catch(ClassifyPartException e){
            logger.error("cpu 분류 중 에러 발생", e);
            exceptionCount++;
        }

        try{
            partList = ramRepository.findAllByClassColumn(null);
            recommendUtil.classifyRam(partList);
        } catch(ClassifyPartException e){
            logger.error("ram 분류 중 에러 발생", e);
            exceptionCount++;
        }
        try{
            partList = gpuRepository.findAllByClassColumn(null);
            recommendUtil.classifyGpu(partList);
        } catch(ClassifyPartException e){
            logger.error("gpu 분류 중 에러 발생", e);
            exceptionCount++;
        }
        try{
            partList = powerRepository.findAllByClassColumn(null);
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

    public List<QuoteResponseDto> createRecommend(QuoteRequestDto quoteRequestDto){
        /*
        cpu, ram, gpu는 quotecandidate로 정해져있음.
        남은 ssd, mainboard, case, power를 정해야함.
        ssd는 유저 입력값에 따라 조절하고
        mainboard는 cpu, ram, ssd, 용도에 따라 정하고
        case는 mainboard, gpu에 따라 정해져있다.
        power는 위 부품들의 필요전력 이상, 용도에 해당하는 등급 이상으로 추천한다.
         */
        int budget = quoteRequestDto.getBudget();

        List<QuoteCandidateEntity> quoteCandidateList = quoteCandidateRepository.findAll();
        List<QuoteResponseDto> responseList = new ArrayList<>();
        //SSD > Mainboard > Chassis > Power 순으로 List 생성
        for(QuoteCandidateEntity quoteCandidate : quoteCandidateList){
            CpuEntity cpu = quoteCandidate.getCpu();
            RamEntity ram = quoteCandidate.getRam();
            GpuEntity gpu = quoteCandidate.getGpu();
            int budgetLeft = budget - quoteCandidate.getTotalPrice();

            String usage = quoteRequestDto.getUsage();
            String caseSize = quoteRequestDto.getCaseSize();
            /* 현재 미사용중
            double ssdSize = quoteRequestDto.getSsdSize();
            int priority = quoteRequestDto.getPriority();
            boolean as = quoteRequestDto.isAs();*/

            //1.SSD는 사용자가 선택한 용량에 따라 필터링
            List<SsdEntity> ssdList = ssdRepository.findByCapacity(new BigDecimal(quoteRequestDto.getSsdSize()/1000.0));
            logger.debug("ssd 용량기반으로 리스트화 완료, "+ssdList.size());
            for(SsdEntity ssd : ssdList){
                if(budgetLeft < ssd.getPrice()) continue;
                /*
                2. Mainboard는
                    유저가 고른 caseSize와 mainboard의 size가 맞아야하고
                    cpu의 socket_info가 mainboard의 socket_info와 일치하고
                    ram의 memory_spec과 mainboard의 memory_spec이 일치하고
                    ssd의 pcie_ver에 해당하는 mainboard의 pcie_ver이 true여야 하고
                    class가 quoteRequestDto의 usage에 따른 class와 일치해야한다.
                 */
                int requiredClass = recommendUtil.getClassByUsageAndPartType(usage,"mainboard");

                List<MainboardEntity> mainboardList = mainboardRepository.findByCaseSizeSocketAndClassAndMemoryAndPcie(
                        caseSize,
                        quoteCandidate.getCpu().getSocketInfo(),
                        requiredClass,
                        quoteCandidate.getRam().getMemorySpec(),
                        ssd.getPcieVer()
                );

                logger.debug("사용자쿼리 findBySocketAndClassAndMemoryAndPcie 성공, "+mainboardList.size());

                for(MainboardEntity mainboard : mainboardList){
                    if(budgetLeft - ssd.getPrice() < mainboard.getPrice()) continue;
                    /*
                    3. Chassis는
                        mainboard의 size에 따른 chassis의 ~_atx컬럼이 true여야 하고
                        gpu의 길이 < chassis의 depth 여야한다.
                     */
                    List<ChassisEntity> chassisList = chassisRepository.findByCaseSizeAndDepth(caseSize, gpu.getWidth());//size(ATX), max_gpu_depth,
                    for(ChassisEntity chassis : chassisList){
                        if(budgetLeft - ssd.getPrice() - mainboard.getPrice() < chassis.getPrice()) continue; //todo:총액 계산 메소드 제작필요
                        //int totalPower = recommendUtil.getTotalPower(cpu, ram, gpu, ssd, mainboard, chassis)
                        List<PowerEntity> powerList = powerRepository.findByChassisAndClass(
                                chassis.getMaxPowerDepth(),
                                recommendUtil.getClassByUsageAndPartType(usage,"power")
                        ); //출력, 깊이=샤씨깊이, 분류
                        for(PowerEntity power : powerList){
                            if(budgetLeft - ssd.getPrice() - mainboard.getPrice() - chassis.getPrice() < power.getPrice()) continue;
                            QuoteResponseDto responseDto = QuoteResponseDto.builder()
                                    .cpu(cpu)
                                    .ram(ram)
                                    .gpu(gpu)
                                    .ssd(ssd)
                                    .mainboard(mainboard)
                                    .chassis(chassis)
                                    .power(power)
                                    .totalPrice(cpu.getPrice()+ram.getPrice()+gpu.getPrice()+ssd.getPrice()+mainboard.getPrice()+chassis.getPrice()+power.getPrice())
                                    .build();
                            responseList.add(responseDto);
                        }
                    }
                }
            }
        } //responseDto 삽입 완료
        //todo:연산이 너무 많음. 중간중간에서 리스트의 가지수를 줄이는 로직 추가 필요.

        return responseList;
    }

    public List<?> getPartRecommend(PartRequestDto partRequestDto) {

        //카테고리별로 함수에서 진행
        List<?> partDtoList = switch(partRequestDto.getCategory()){
            case "cpu" -> searchCpu(partRequestDto);
//            case "ram" ->
//            case "gpu" ->
//            case "power" ->
            default -> { yield null;}
        };

        return partDtoList;
    }

    private List<CpuResponseDto> searchCpu(PartRequestDto partRequestDto) {

        //1. 분류된(class가 있는) 부품은 용도에 따른 분류값 지정
        int cpuClass = recommendUtil.getClassByUsageAndPartType(partRequestDto.getUsage(),"cpu");
        //2. 성능기준치가 있는 부품은 성능기준치 지정
        List<CpuEntity> listCpu = cpuRepository.findAllByClassColumn(cpuClass);

        //3. 우선순위에 따라 정렬방식 변경
        Comparator<CpuEntity> comparator;
        switch(partRequestDto.getPriority()){
            case PrioritySelector.PERFORMANCE_FIRST -> {
                comparator = Comparator.comparingInt(cpu -> cpu.getSingleScore());
                Collections.sort(listCpu, comparator.reversed());
            }
            case PrioritySelector.PRICE_FIRST -> {
                comparator = Comparator.comparingInt(cpu -> cpu.getPrice());
                Collections.sort(listCpu, comparator);
            }
            case PrioritySelector.PERFORMANCE_PER_PRICE -> {
                 comparator = Comparator.comparingInt(cpu -> cpu.getSingleScore()/cpu.getPrice());
                Collections.sort(listCpu, comparator.reversed());
            }
            default -> throw new ArithmeticException();
        }
        ModelMapper modelMapper = new ModelMapper();
//        List<CpuResponseDto> listCpuDto = modelMapper.map(cpuEntity, CpuResponseDto.class);
        return null;
        /*
                                private String category;
        private String usage;
        private int budget;
        private int priority;
        private boolean as;
         */
        /*
        1. 용도별 분류가 된 부품(CPU, RAM, GPU, MAINBOARD, POWER)은 매칭되는 CLASS 필터링 / SSD CHASSIS COOLER
        2. AS=TRUE & POWER, COOLER면 보증기간 체크
        3. 가성비/성능/가격 별로 정렬 -> 우선순위 기준. 우선순위 -1 성능 0가성비 1가격
        3-1. 가격ASC or 성능컬럼/가격DESC or 성능DESC
         - 성능 >
         CPU : 싱글코어점수single_score
         RAM : 메모리스펙, 메모리클럭
         GPU : score
         MAINBOARD : X(class만 사용)
         POWER : X(class만 사용)
         CHASSIS : X
         SSD : reading_speed
         cooler : fan_count
         */
    }
}
