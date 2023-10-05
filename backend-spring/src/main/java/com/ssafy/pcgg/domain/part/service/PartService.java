package com.ssafy.pcgg.domain.part.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.ssafy.pcgg.domain.part.PartTypeFormat;
import com.ssafy.pcgg.domain.part.repository.ChassisRepository;
import com.ssafy.pcgg.domain.part.repository.CoolerRepository;
import com.ssafy.pcgg.domain.part.repository.CpuRepository;
import com.ssafy.pcgg.domain.part.repository.GpuRepository;
import com.ssafy.pcgg.domain.part.repository.MainboardRepository;
import com.ssafy.pcgg.domain.part.repository.PowerRepository;
import com.ssafy.pcgg.domain.part.repository.RamRepository;
import com.ssafy.pcgg.domain.part.repository.SsdRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PartService {

	private final CpuRepository cpuRepository;
	private final MainboardRepository mainboardRepository;
	private final SsdRepository ssdRepository;
	private final RamRepository ramRepository;
	private final GpuRepository gpuRepository;
	private final ChassisRepository chassisRepository;
	private final PowerRepository powerRepository;
	private final CoolerRepository coolerRepository;
	public Slice<?> searchParts(String type, int pages, String keyword){
		if(keyword.length() < 2){
			throw new IllegalArgumentException("검색어의 길이는 최소 2글자 이상이어야 합니다.");
		}

		PageRequest pageRequest = PageRequest.of(pages, 30);

		switch (type) {
			case PartTypeFormat.CPU:
				return cpuRepository.findSliceByNameContaining(pageRequest, keyword);
			case PartTypeFormat.MAINBOARD:
				return mainboardRepository.findSliceByNameContaining(pageRequest, keyword);
			case PartTypeFormat.SSD:
				return ssdRepository.findSliceByNameContaining(pageRequest, keyword);
			case PartTypeFormat.RAM:
				return ramRepository.findSliceByNameContaining(pageRequest, keyword);
			case PartTypeFormat.GPU:
				return gpuRepository.findSliceByNameContaining(pageRequest, keyword);
			case PartTypeFormat.CHASSIS:
				return chassisRepository.findSliceByNameContaining(pageRequest, keyword);
			case PartTypeFormat.POWER:
				return powerRepository.findSliceByNameContaining(pageRequest, keyword);
			case PartTypeFormat.COOLER:
				return coolerRepository.findSliceByNameContaining(pageRequest, keyword);
			default:
				throw new IllegalArgumentException("유효하지 않은 부품 타입입니다.");
		}
	}

	public Slice<?> getParts(String type, int pages){
		PageRequest pageRequest = PageRequest.of(pages, 30);

		switch (type) {
			case PartTypeFormat.CPU:
				return cpuRepository.findSliceBy(pageRequest);
			case PartTypeFormat.MAINBOARD:
				return mainboardRepository.findSliceBy(pageRequest);
			case PartTypeFormat.SSD:
				return ssdRepository.findSliceBy(pageRequest);
			case PartTypeFormat.RAM:
				return ramRepository.findSliceBy(pageRequest);
			case PartTypeFormat.GPU:
				return gpuRepository.findSliceBy(pageRequest);
			case PartTypeFormat.CHASSIS:
				return chassisRepository.findSliceBy(pageRequest);
			case PartTypeFormat.POWER:
				return powerRepository.findSliceBy(pageRequest);
			case PartTypeFormat.COOLER:
				return coolerRepository.findSliceBy(pageRequest);
			default:
				throw new IllegalArgumentException("유효하지 않은 부품 타입입니다.");
		}
	}

}
