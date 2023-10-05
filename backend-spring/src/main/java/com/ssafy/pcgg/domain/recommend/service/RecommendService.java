package com.ssafy.pcgg.domain.recommend.service;

import com.ssafy.pcgg.domain.part.dto.*;
import com.ssafy.pcgg.domain.part.entity.*;
import com.ssafy.pcgg.domain.part.exception.NoSuchPartTypeException;
import com.ssafy.pcgg.domain.part.repository.*;
import com.ssafy.pcgg.domain.recommend.dto.*;
import com.ssafy.pcgg.domain.recommend.entity.*;
import com.ssafy.pcgg.domain.recommend.exception.*;
import com.ssafy.pcgg.domain.recommend.repository.*;
import com.ssafy.pcgg.domain.recommend.util.PrioritySelector;
import com.ssafy.pcgg.domain.recommend.util.RecommendUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RecommendUtil recommendUtil;
    private final Logger logger = LoggerFactory.getLogger(RecommendService.class.getName());
    private final QuoteCandidateRepository quoteCandidateRepository;
    private final QuoteRepository quoteRepository;
    private final QuoteSavedRepository quoteSavedRepository;
    private final UsageNsRepository usageNsRepository;
    private final CpuRepository cpuRepository;
    private final RamRepository ramRepository;
    private final GpuRepository gpuRepository;
    private final PowerRepository powerRepository;
    private final SsdRepository ssdRepository;
    private final MainboardRepository mainboardRepository;
    private final ChassisRepository chassisRepository;
    private final LaptopRepository laptopRepository;
    private final CoolerRepository coolerRepository;

    private final ModelMapper modelMapper;

    @Transactional
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

    @Transactional
    public void classifyPart() throws ClassifyPartException{
        List<?> partList;
        int exceptionCount = 0;
        //exception이 발생해도 다른 부품 분류 계속 진행
        try {
            partList = cpuRepository.findAllByClassColumn(null);
            recommendUtil.classifyCpu(partList);
            logger.trace("ClassifyPart - cpu 분류 종료");
        } catch(ClassifyPartException e){
            logger.error("cpu 분류 중 에러 발생", e);
            exceptionCount++;
        }

        try{
            partList = ramRepository.findAllByClassColumn(null);
            recommendUtil.classifyRam(partList);
            logger.trace("ClassifyPart - RAM 분류 종료");
        } catch(ClassifyPartException e){
            logger.error("ram 분류 중 에러 발생", e);
            exceptionCount++;
        }
        try{
            partList = gpuRepository.findAllByClassColumn(null);
            recommendUtil.classifyGpu(partList);
            logger.trace("ClassifyPart - GPU 분류 시작");
        } catch(ClassifyPartException e){
            logger.error("gpu 분류 중 에러 발생", e);
            exceptionCount++;
        }
        try{
            partList = powerRepository.findByClassColumn(null);
            recommendUtil.classifyPower(partList);
            logger.trace("ClassifyPart - POWER 분류 시작");
        } catch(ClassifyPartException e){
            logger.error("cpu 분류 중 에러 발생", e);
            exceptionCount++;
        }

        try{
            partList = mainboardRepository.findByClassColumn(null);
            recommendUtil.classifyMainboard(partList);
            logger.trace("ClassifyPart - POWER 분류 시작");
        } catch(ClassifyPartException e){
            logger.error("cpu 분류 중 에러 발생", e);
            exceptionCount++;
        }
        logger.trace("exceptionCount = "+exceptionCount);
        if(exceptionCount==5) throw new ClassifyPartAllFailedException();
        logger.trace("ClassifyPart 메소드 종료");
    }

    @Transactional
    public void deleteAndCreateQuoteCandidate() {
        //생성 전 기존 리스트 제거
        //deleteCandidate

        //견적용도별로 새로운 리스트 산출
        //createCandidatea
        List<UsageNsEntity> usageList = usageNsRepository.findAll();
        for(UsageNsEntity usage : usageList){
            logger.trace("loop반복중. 현재 usage = "+usage.getName());
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
        checkNull(quoteRequestDto);
        Integer budget = quoteRequestDto.getBudget();
        UsageNsEntity usage = usageNsRepository.findById(quoteRequestDto.getUsage()).orElseThrow(() -> new NullPointerException("해당 이름을 가지는 UsageEntity 없음"));
        String caseSize = quoteRequestDto.getCaseSize();

        List<QuoteCandidateEntity> quoteCandidateList = new ArrayList<>();
        //해당 용도의 견적후보중에서 가성비, 성능, 가격이 제일 좋은 후보 하나씩 pick
//        quoteCandidateList.add(quoteCandidateRepository.findMostPriceEfficientByUsage(usage));
//        quoteCandidateList.add(quoteCandidateRepository.findCheapestByUsage(usage));
//        quoteCandidateList.add(quoteCandidateRepository.findBestPerformanceByUsage(usage));

        //후보군이 너무 적으면 여러개 pick
        for(QuoteCandidateEntity quoteCandidate : quoteCandidateRepository.findAllMostPriceEfficientByUsage(usage)){
            quoteCandidateList.add(quoteCandidate);
        }
        for(QuoteCandidateEntity quoteCandidate : quoteCandidateRepository.findAllCheapestByUsage(usage)){
            quoteCandidateList.add(quoteCandidate);
        }
        for(QuoteCandidateEntity quoteCandidate : quoteCandidateRepository.findAllBestPerformanceByUsage(usage)){
            quoteCandidateList.add(quoteCandidate);
        }
        logger.trace("최초후보 사이즈 "+quoteCandidateList.size());
        List<QuoteResponseDto> responseCandidateList = new ArrayList<>();
        //Mainboard > Chassis > Power 순으로 List 생성
        for(QuoteCandidateEntity quoteCandidate : quoteCandidateList){
            CpuEntity cpu = quoteCandidate.getCpu();
            RamEntity ram = quoteCandidate.getRam();
            GpuEntity gpu = quoteCandidate.getGpu();
            int budgetLeft = budget - quoteCandidate.getTotalPrice();
                /*
                2. Mainboard는
                    유저가 고른 caseSize와 mainboard의 size가 맞아야하고
                    cpu의 socket_info가 mainboard의 socket_info와 일치하고
                    ram의 memory_spec과 mainboard의 memory_spec이 일치하고
                    ssd의 pcie_ver에 해당하는 mainboard의 pcie_ver이 true여야 하고
                    class가 quoteRequestDto의 usage에 따른 class와 일치해야한다.
                 */
                int requiredClass = recommendUtil.getClassByUsageAndPartType(usage.getName(),"mainboard");

                List<MainboardEntity> mainboardList = mainboardRepository.findByCaseSizeSocketAndClassAndMemoryAndPcie(
                        caseSize
                        ,requiredClass
                        ,quoteCandidate.getRam().getMemorySpec()
                );

                for(MainboardEntity mainboard : mainboardList){
                    if(budgetLeft < mainboard.getPrice()) continue;
                    /*
                    3. Chassis는
                        mainboard의 size에 따른 chassis의 ~_atx컬럼이 true여야 하고
                        gpu의 길이 < chassis의 depth 여야한다.
                     */
                    List<ChassisEntity> chassisList = chassisRepository.findByCaseSizeAndDepth(caseSize, gpu.getWidth());//size(ATX), max_gpu_depth,
                    logger.trace("size:"+caseSize+" width "+gpu.getWidth()+"에 맞는 case목록 :"+chassisList.size());
                    for(ChassisEntity chassis : chassisList){
                        if(budgetLeft - mainboard.getPrice() < chassis.getPrice()) continue;
                        List<PowerEntity> powerList = powerRepository.findByChassisAndClass(
                                chassis.getMaxPowerDepth(),
                                recommendUtil.getClassByUsageAndPartType(usage.getName(),"power")
                        ); //출력, 깊이=샤씨깊이, 분류
                        for(PowerEntity power : powerList){
                            if(budgetLeft - mainboard.getPrice() - chassis.getPrice() < power.getPrice()) continue;
                            QuoteResponseDto responseDto = QuoteResponseDto.builder()
                                    .cpu(cpu)
                                    .ram(ram)
                                    .gpu(gpu)
                                    .mainboard(mainboard)
                                    .chassis(chassis)
                                    .power(power)
                                    .totalPrice(cpu.getPrice()+ram.getPrice()+gpu.getPrice()+mainboard.getPrice()+chassis.getPrice()+power.getPrice())
                                    .benchScore(cpu.getSingleScore()+ram.getCapacity()+gpu.getScore()+mainboard.getClassColumn()*1000+power.getClassColumn()*1000)
                                    .build();
                            responseCandidateList.add(responseDto);
                        }
                    }
                }
            }
        List<QuoteResponseDto> responseList = new ArrayList<>();
        if(!responseCandidateList.isEmpty()) {
            responseCandidateList.sort(Comparator.comparingInt(QuoteResponseDto::getTotalPrice));
            responseList.add(responseCandidateList.get(0));
            responseCandidateList.sort(Comparator.comparingInt(QuoteResponseDto::getBenchScore).reversed());
            responseList.add(responseCandidateList.get(0));
            Comparator<QuoteResponseDto> comparator = Comparator.comparingInt(quote -> quote.getBenchScore() / quote.getTotalPrice());
            responseCandidateList.sort(comparator.reversed());
            responseList.add(responseCandidateList.get(0));
        }
        return responseList;
    }

    private void checkNull(QuoteRequestDto quoteRequestDto) {
        if(quoteRequestDto.getBudget()==null || quoteRequestDto.getBudget() == 0){
            quoteRequestDto.setBudget(10000000);
        }
        if(quoteRequestDto.getUsage() == null || quoteRequestDto.getUsage().equals("")){
            quoteRequestDto.setUsage("캐주얼게임");
        }
        if(quoteRequestDto.getCaseSize() == null || quoteRequestDto.getCaseSize().equals("")){
            quoteRequestDto.setCaseSize("M-ATX");
        }
    }

    public List<?> getPartRecommend(PartRequestDto partRequestDto) {
        //1. 용도별 분류가 된 부품(CPU, RAM, GPU, MAINBOARD, POWER)은 매칭되는 CLASS 필터링 / exclude SSD CHASSIS COOLER
        List<?> listPart = getPartList(partRequestDto);
        logger.trace(listPart.size()+" < size");

        //2. 우선순위에 따라 정렬방식 변경
        listPart = getComparatorAndSort(listPart, partRequestDto);

        //Entity > DTO 매핑 후 리턴
        return mapToDto(partRequestDto.getCategory(), listPart);
    }

    private List<?> mapToDto(String category, List<?> listPart) {
        switch(category){
            case "cpu" ->{
                List<CpuResponseDto> listCpuDto = listPart.stream()
                        .map(cpu -> modelMapper.map(cpu, CpuResponseDto.class))
                        .collect((Collectors.toList()));
                listCpuDto.forEach(cpu -> logger.info(cpu.toString()));
                return listCpuDto;
            }
            case "ram" ->{
                List<RamResponseDto> listRamDto = listPart.stream()
                        .map(ram -> modelMapper.map(ram, RamResponseDto.class))
                        .collect((Collectors.toList()));
                listRamDto.forEach(ram -> logger.info(ram.toString()));
                return listRamDto;
            }
            case "ssd" ->{
                List<SsdResponseDto> listSsdDto = listPart.stream()
                        .map(ssd -> modelMapper.map(ssd, SsdResponseDto.class))
                        .collect((Collectors.toList()));
                listSsdDto.forEach(ssd -> logger.info(ssd.toString()));
                return listSsdDto;
            }
            case "power" ->{
                List<PowerResponseDto> listPowerDto = listPart.stream()
                        .map(power -> modelMapper.map(power, PowerResponseDto.class))
                        .collect((Collectors.toList()));
                listPowerDto.forEach(power -> logger.info(power.toString()));
                return listPowerDto;
            }
            case "mainboard" ->{
                List<MainboardResponseDto> listMainboardDto = listPart.stream()
                        .map(mainboard -> modelMapper.map(mainboard, MainboardResponseDto.class))
                        .collect((Collectors.toList()));
                listMainboardDto.forEach(mainboard -> logger.info(mainboard.toString()));
                return listMainboardDto;
            }
            case "cooler" ->{
                List<CoolerResponseDto> listCoolerDto = listPart.stream()
                        .map(cooler -> modelMapper.map(cooler, CoolerResponseDto.class))
                        .collect((Collectors.toList()));
                listCoolerDto.forEach(cooler -> logger.info(cooler.toString()));
                return listCoolerDto;
            }
            case "chassis" ->{
                List<ChassisResponseDto> listChassisDto = listPart.stream()
                        .map(chassis -> modelMapper.map(chassis, ChassisResponseDto.class))
                        .collect((Collectors.toList()));
                listChassisDto.forEach(chassis -> logger.info(chassis.toString()));
                return listChassisDto;
            }
            case "gpu" ->{
                List<GpuResponseDto> listGpuDto = listPart.stream()
                        .map(gpu -> modelMapper.map(gpu, GpuResponseDto.class))
                        .collect((Collectors.toList()));
                listGpuDto.forEach(gpu -> logger.info(gpu.toString()));
                return listGpuDto;
            }
            default -> throw new NoSuchPartTypeException();
        }
    }

    @SuppressWarnings("unchecked")
    private List<?> getComparatorAndSort(List<?> listPart, PartRequestDto partRequestDto) {
        int priority = partRequestDto.getPriority();
        switch(partRequestDto.getCategory()){
            case "cpu"-> {
                List<CpuEntity> listCpu = (List<CpuEntity>) listPart;
                switch(priority){
                    case PrioritySelector.PERFORMANCE_FIRST -> listCpu.sort(Comparator.comparingInt(CpuEntity::getSingleScore).reversed());
                    case PrioritySelector.PRICE_FIRST -> listCpu.sort(Comparator.comparingInt(CpuEntity::getPrice));
                    case PrioritySelector.PERFORMANCE_PER_PRICE -> {
                        Comparator<CpuEntity> comparator = Comparator.comparingInt(cpu -> cpu.getSingleScore()/cpu.getPrice());
                        listCpu.sort(comparator.reversed());
                    }
                    default -> throw new NoSuchPriorityException(priority);
                }
                return listCpu;
            }
            case "ram" -> {
                List<RamEntity> listRam = (List<RamEntity>) listPart;
                switch(priority){
                    case PrioritySelector.PERFORMANCE_FIRST -> listRam.sort(Comparator.comparingInt(RamEntity::getMemoryClock).reversed());
                    case PrioritySelector.PRICE_FIRST -> listRam.sort(Comparator.comparingInt(RamEntity::getPrice));
                    case PrioritySelector.PERFORMANCE_PER_PRICE -> {
                        Comparator<RamEntity> comparator = Comparator.comparingInt(ram -> ram.getMemoryClock()/ram.getPrice());
                        listRam.sort(comparator.reversed());
                    }
                    default -> throw new NoSuchPriorityException(priority);
                }
                return listRam;
            }
            case "gpu" -> {
                List<GpuEntity> listGpu = (List<GpuEntity>) listPart;
                switch(priority){
                    case PrioritySelector.PERFORMANCE_FIRST -> listGpu.sort(Comparator.comparingInt(GpuEntity::getScore).reversed());
                    case PrioritySelector.PRICE_FIRST -> listGpu.sort(Comparator.comparingInt(GpuEntity::getPrice));
                    case PrioritySelector.PERFORMANCE_PER_PRICE -> {
                        Comparator<GpuEntity> comparator = Comparator.comparingInt(gpu -> gpu.getScore()/gpu.getPrice());
                        listGpu.sort(comparator.reversed());
                    }
                    default -> throw new NoSuchPriorityException(priority);
                }
                return listGpu;
            }
            case "mainbaord" -> {
                List<MainboardEntity> listMainboard = (List<MainboardEntity>) listPart;
                switch(priority){
                    case PrioritySelector.PERFORMANCE_FIRST -> {/*파워는 성능고려X*/}
                    case PrioritySelector.PRICE_FIRST -> listMainboard.sort(Comparator.comparingInt(MainboardEntity::getPrice));
                    case PrioritySelector.PERFORMANCE_PER_PRICE -> {/*성능 없으므로 가성비 없음*/}
                    default -> throw new NoSuchPriorityException(priority);
                }
                return listMainboard;
            }
            case "power" -> {
                List<PowerEntity> listPower = (List<PowerEntity>) listPart;
                switch(priority){
                    case PrioritySelector.PERFORMANCE_FIRST -> {/*파워는 성능고려X*/}
                    case PrioritySelector.PRICE_FIRST -> listPower.sort(Comparator.comparingInt(PowerEntity::getPrice));
                    case PrioritySelector.PERFORMANCE_PER_PRICE -> {/*성능 없으므로 가성비 없음*/}
                    default -> throw new NoSuchPriorityException(priority);
                }
                return listPower;
            }
            case "cooler" -> {
                List<CoolerEntity> listCooler = (List<CoolerEntity>) listPart;
                switch(priority){
                    case PrioritySelector.PERFORMANCE_FIRST -> listCooler.sort(Comparator.comparingInt(CoolerEntity::getFanCount).reversed());
                    case PrioritySelector.PRICE_FIRST -> listCooler.sort(Comparator.comparingInt(CoolerEntity::getPrice));
                    case PrioritySelector.PERFORMANCE_PER_PRICE -> {
                        Comparator<CoolerEntity> comparator = Comparator.comparingInt(cooler -> cooler.getFanCount()/cooler.getPrice());
                        listCooler.sort(comparator.reversed());
                    }
                    default -> throw new NoSuchPriorityException(priority);
                }
                return listCooler;
            }
            case "chassis" -> {
                List<ChassisEntity> listChassis = (List<ChassisEntity>) listPart;
                switch(priority){
                    case PrioritySelector.PERFORMANCE_FIRST -> {/*케이스는 성능고려X*/}
                    case PrioritySelector.PRICE_FIRST -> listChassis.sort(Comparator.comparingInt(ChassisEntity::getPrice));
                    case PrioritySelector.PERFORMANCE_PER_PRICE -> {/*성능 없으므로 가성비 없음*/}
                    default -> throw new NoSuchPriorityException(priority);
                }
                return listChassis;
            }
            case "ssd" ->{
                List<SsdEntity> listSsd = (List<SsdEntity>) listPart;
                switch(priority){
                    case PrioritySelector.PERFORMANCE_FIRST -> listSsd.sort(Comparator.comparingInt(SsdEntity::getReadingSpeed).reversed());
                    case PrioritySelector.PRICE_FIRST -> listSsd.sort(Comparator.comparingInt(SsdEntity::getPrice));
                    case PrioritySelector.PERFORMANCE_PER_PRICE -> {
                        Comparator<SsdEntity> comparator = Comparator.comparingInt(ssd -> ssd.getReadingSpeed()/ssd.getPrice());
                        listSsd.sort(comparator.reversed());
                    }
                    default -> throw new NoSuchPriorityException(priority);
                }
                return listSsd;
            }
            default -> throw new NoSuchPartTypeException(partRequestDto.getCategory());
        }
    }

    private List<?> getPartList(PartRequestDto partRequestDto) {
        String category = partRequestDto.getCategory();
        String usage = partRequestDto.getUsage();
        int budget = partRequestDto.getBudget();
        int partClass = recommendUtil.getClassByUsageAndPartType(usage,category);

        //용도별 분류가 된 부품(CPU, RAM, GPU, MAINBOARD, POWER)은 매칭되는 CLASS 필터링, AS==TRUE & POWER, COOLER면 보증기간도 체크
        switch(category){
            case "cpu"-> {
                return cpuRepository.findAllByClassColumnAndPriceLessThanEqual(partClass, budget);
            }
            case "ram" -> {
                return ramRepository.findAllByClassColumnAndPriceLessThanEqual(partClass, budget);
            }
            case "gpu" -> {
                return gpuRepository.findAllByClassColumnAndPriceLessThanEqual(partClass, budget);
            }
            case "mainbaord" -> {
                return mainboardRepository.findAllByClassColumnAndPriceLessThanEqual(partClass, budget);
            }
            case "power" -> {
                if (partRequestDto.isAs()) return powerRepository.findAllByClassColumnAndWarrantyPeriodAndBudget(partClass, budget);
                else return powerRepository.findByClassColumn(partClass);
            }
            case "cooler" -> {
                if (partRequestDto.isAs()) return coolerRepository.findAllByWarrantyPeriodAndBudget(budget);
                else return coolerRepository.findAll();
            }
            case "chassis" -> {
                return chassisRepository.findAllByBudget(budget);
            }
            case "ssd" -> {
                return ssdRepository.findAllByBudget(budget);
            }
            default -> throw new NoSuchPartTypeException(category);
        }
    }
    public void saveQuoteRecommendToMyPage(SaveQuoteRequestDto saveQuoteRequestDto) {
        try {
            QuoteEntity quote = QuoteEntity.builder()
                    .chassisId(saveQuoteRequestDto.getChassisId())
                    .cpuId(saveQuoteRequestDto.getCpuId())
                    .gpuId(saveQuoteRequestDto.getGpuId())
                    .coolerId(saveQuoteRequestDto.getCoolerId())
                    .mainboardId(saveQuoteRequestDto.getMainboardId())
                    .powerId(saveQuoteRequestDto.getPowerId())
                    .ramId(saveQuoteRequestDto.getRamId())
                    .ssdId(saveQuoteRequestDto.getSsdId())
                    .build();
            quoteRepository.save(quote);

            QuoteSaved quoteSaved = QuoteSaved.builder()
                    .quoteId(quote.getId())
                    .userId(saveQuoteRequestDto.getUserId())
                    .build();
            quoteSavedRepository.save(quoteSaved);
        }catch(SavingQuoteException e){
            logger.error("견적 저장 중 에러 발생:",e.getMessage(),e.getCause());
            throw e;
        }
    }

    public List<LaptopResponseDto> getLaptopRecommend(LaptopRequestDto laptopRequestDto){
        String usage = laptopRequestDto.getUsage();
        List<LaptopEntity> laptopList;
        switch(usage){
            case "저" -> laptopList = laptopRepository.findByOsAndBudget(laptopRequestDto.isOs(), laptopRequestDto.getBudget(),"i3");
            case "중" -> {
                laptopList = laptopRepository.findByOsAndBudget(laptopRequestDto.isOs(), laptopRequestDto.getBudget(),"i5");
                laptopList.addAll(laptopRepository.findByOsAndBudget(laptopRequestDto.isOs(), laptopRequestDto.getBudget(),"i7"));
            }
            default -> laptopList = laptopRepository.findByOsAndBudget(laptopRequestDto.isOs(), laptopRequestDto.getBudget(),"i9");
        }
        List<LaptopResponseDto> laptopResponseDtoList = laptopList.stream()
                .map(laptop -> modelMapper.map(laptop, LaptopResponseDto.class))
                .collect((Collectors.toList()));
        return laptopResponseDtoList;
    }

}

