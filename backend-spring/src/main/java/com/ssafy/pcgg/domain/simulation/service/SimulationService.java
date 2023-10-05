package com.ssafy.pcgg.domain.simulation.service;

import com.ssafy.pcgg.domain.part.entity.*;
import com.ssafy.pcgg.domain.part.repository.*;
import com.ssafy.pcgg.domain.simulation.dto.PartCheckRequestDto;
import com.ssafy.pcgg.domain.simulation.dto.PartCheckResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SimulationService {

	private final CpuRepository cpuRepository;
	private final MainboardRepository mainboardRepository;
	private final SsdRepository ssdRepository;
	private final RamRepository ramRepository;
	private final GpuRepository gpuRepository;
	private final ChassisRepository chassisRepository;
	private final PowerRepository powerRepository;
	private final CoolerRepository coolerRepository;

	@Transactional
	public List<PartCheckResponseDto> checkCompatibility(PartCheckRequestDto partCheckRequestDto) {
		ChassisEntity chassis = chassisRepository.findById(Long.valueOf(partCheckRequestDto.getCaseId())).orElse(null);
		CoolerEntity cooler = coolerRepository.findById(Long.valueOf(partCheckRequestDto.getCoolerId())).orElse(null);
		GpuEntity gpu = gpuRepository.findById(Long.valueOf(partCheckRequestDto.getGpuId())).orElse(null);
		CpuEntity cpu = cpuRepository.findById(Long.valueOf(partCheckRequestDto.getCpuId())).orElse(null);

		RamEntity ram = ramRepository.findById(Long.valueOf(partCheckRequestDto.getRamId())).orElse(null);
		MainboardEntity mainboard = mainboardRepository.findById(Long.valueOf(partCheckRequestDto.getMainboardId())).orElse(null);
		PowerEntity power = powerRepository.findById(Long.valueOf(partCheckRequestDto.getPowerId())).orElse(null);
		SsdEntity ssd = ssdRepository.findById(Long.valueOf(partCheckRequestDto.getSsdId())).orElse(null);

		List<PartCheckResponseDto> responseDtoList = new ArrayList<>();
		int neededPower=0;
		if(chassis != null){
			check(responseDtoList, chassis, cooler);
			check(responseDtoList, chassis, gpu);
			check(responseDtoList, chassis, mainboard);
			check(responseDtoList, chassis, power);
		}
//		if(cooler != null){}
//		if(gpu != null){}
		if(cpu != null){
			check(responseDtoList, cpu, ram);
			check(responseDtoList, cpu, mainboard);
		}
		if (ram != null) {
			check(responseDtoList, ram, mainboard);
		}
		if(mainboard != null) {
			check(responseDtoList, mainboard, ssd);
		}
//		if(power != null){}
//		if(ssd != null){}
		return responseDtoList;
	}

	private void check(List<PartCheckResponseDto> responseDtoList, RamEntity ram, MainboardEntity mainboard) {
		if(mainboard == null) return;
		if(!mainboard.getMemorySpec().equals(ram.getMemorySpec())) responseDtoList.add(new PartCheckResponseDto("ram","mainboard","메모리와 메인보드 DDR 버전 상이"));
	}

	private void check(List<PartCheckResponseDto> responseDtoList, MainboardEntity mainboard, SsdEntity ssd) {
		if(ssd == null) return;
		switch(ssd.getPcieVer()){
			case 3 -> {if(!mainboard.getPcie3()) responseDtoList.add(new PartCheckResponseDto("mainboard","ssd","메인보드와 SSD의 pcie버전 상이. SSD pcie ver.3"));}
			case 4 -> {if(!mainboard.getPcie4()) responseDtoList.add(new PartCheckResponseDto("mainboard","ssd","메인보드와 SSD의 pcie버전 상이. SSD pcie ver.4"));}
			case 5 -> {if(!mainboard.getPcie5()) responseDtoList.add(new PartCheckResponseDto("mainboard","ssd","메인보드와 SSD의 pcie버전 상이. SSD pcie ver.5"));}
			default -> {}
		}
	}

	private void check(List<PartCheckResponseDto> responseDtoList, CpuEntity cpu, MainboardEntity mainboard) {
		if(mainboard == null) return;
		if(!cpu.getSocketInfo().equals(mainboard.getSocketInfo())) responseDtoList.add(new PartCheckResponseDto("cpu","mainboard","소켓버전 상이. CPU:"+cpu.getSocketInfo()+" vs. 메인보드:"+mainboard.getSocketInfo()));
	}

	private void check(List<PartCheckResponseDto> responseDtoList, CpuEntity cpu, RamEntity ram) {
		if(ram == null) return;
		switch(ram.getMemorySpec()){
			case "DDR4" -> {if(!cpu.getDdr4()) responseDtoList.add(new PartCheckResponseDto("cpu","ram","CPU가 지원하지 않는 메모리. ram:"+ram.getMemorySpec()));}
			case "DDR5" -> {if(!cpu.getDdr5()) responseDtoList.add(new PartCheckResponseDto("cpu","ram","CPU가 지원하지 않는 메모리. ram:"+ram.getMemorySpec()));}
		}
	}

	private void check(List<PartCheckResponseDto> responseDtoList, ChassisEntity chassis, CoolerEntity cooler) {
		if(cooler == null) return;
		if(chassis.getMaxCoolerDepth().compareTo(cooler.getHeight())<0) responseDtoList.add(new PartCheckResponseDto("case","cooler","쿨러가 케이스 허용치보다 큼"));

	}
	private void check(List<PartCheckResponseDto> responseDtoList, ChassisEntity chassis, GpuEntity gpu) {
		if(gpu == null) return;
		if(chassis.getMaxGpuDepth().compareTo(gpu.getWidth())<0) responseDtoList.add(new PartCheckResponseDto("case","gpu","그래픽 카드가 케이스 허용치보다 큼"));
	}
	private void check(List<PartCheckResponseDto> responseDtoList, ChassisEntity chassis, MainboardEntity mainboard) {
		if(mainboard == null) return;
		switch(mainboard.getSize()){
			case "M-ATX" -> {if(!chassis.getMicroAtx()) responseDtoList.add(new PartCheckResponseDto("case","mainboard","케이스 ↔ 메인보드 사이즈 부적합"));}
			case "M-iTX" -> {if(!chassis.getMiniItx()) responseDtoList.add(new PartCheckResponseDto("case","mainboard","케이스 ↔ 메인보드 사이즈 부적합"));}
			case "ATX" -> {if(!chassis.getStandardAtx()) responseDtoList.add(new PartCheckResponseDto("case","mainboard","케이스 ↔ 메인보드 사이즈 부적합"));}
		}
	}
	private void check(List<PartCheckResponseDto> responseDtoList, ChassisEntity chassis, PowerEntity power) {
		if(power == null) return;
		if(chassis.getMaxPowerDepth().compareTo(power.getDepth())<0) responseDtoList.add(new PartCheckResponseDto("case","power","파워가 케이스 허용치보다 큼"));
	}



}
