package com.ssafy.pcgg.domain.part.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.ssafy.pcgg.domain.part.PartTypeFormat;
import com.ssafy.pcgg.domain.recommend.repository.ChassisRepository;
import com.ssafy.pcgg.domain.recommend.repository.CoolerRepository;
import com.ssafy.pcgg.domain.recommend.repository.CpuRepository;
import com.ssafy.pcgg.domain.recommend.repository.GpuRepository;
import com.ssafy.pcgg.domain.recommend.repository.MainboardRepository;
import com.ssafy.pcgg.domain.recommend.repository.PowerRepository;
import com.ssafy.pcgg.domain.recommend.repository.RamRepository;
import com.ssafy.pcgg.domain.recommend.repository.SsdRepository;

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
	public Slice<?> searchParts(String type, int pages, String name){
		if(name.length() < 2){
			throw new IllegalArgumentException("검색어의 길이는 최소 2글자 이상이어야 합니다.");
		}

		PageRequest pageRequest = PageRequest.of(pages, 30);

		switch (type) {
			case PartTypeFormat.CPU:
				return cpuRepository.findSliceByNameContaining(pageRequest, name);
			case PartTypeFormat.MAINBOARD:
				return mainboardRepository.findSliceByNameContaining(pageRequest, name);
			case PartTypeFormat.SSD:
				return ssdRepository.findSliceByNameContaining(pageRequest, name);
			case PartTypeFormat.RAM:
				return ramRepository.findSliceByNameContaining(pageRequest, name);
			case PartTypeFormat.GPU:
				return gpuRepository.findSliceByNameContaining(pageRequest, name);
			case PartTypeFormat.CHASSIS:
				return chassisRepository.findSliceByNameContaining(pageRequest, name);
			case PartTypeFormat.POWER:
				return powerRepository.findSliceByNameContaining(pageRequest, name);
			case PartTypeFormat.COOLER:
				return coolerRepository.findSliceByNameContaining(pageRequest, name);
			default:
				throw new IllegalArgumentException("유효하지 않은 부품 타입입니다.");
		}
	}
}
