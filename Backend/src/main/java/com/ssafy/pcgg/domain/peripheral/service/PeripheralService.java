package com.ssafy.pcgg.domain.peripheral.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ssafy.pcgg.domain.peripheral.dto.PeripheralResponseDto;
import com.ssafy.pcgg.domain.peripheral.repository.EtcRepository;
import com.ssafy.pcgg.domain.peripheral.repository.KeyboardRepository;
import com.ssafy.pcgg.domain.peripheral.repository.MonitorRepository;
import com.ssafy.pcgg.domain.peripheral.repository.MouseRepository;
import com.ssafy.pcgg.domain.peripheral.repository.PrinterRepository;

@RequiredArgsConstructor
@Service
public class PeripheralService {

	private final KeyboardRepository keyboardRepository;
	private final MonitorRepository monitorRepository;
	private final MouseRepository mouseRepository;
	private final PrinterRepository printerRepository;
	private final EtcRepository etcRepository;

	/**
	* 주변기기 - 키보드
	* 목록조회
	*/
	public Slice<PeripheralResponseDto> keyboardList(int pages) {
		PageRequest pageRequest = PageRequest.of(pages, 5, Sort.by(Sort.Direction.ASC, "id"));
		Slice<PeripheralResponseDto> keyboardResponseSlice =  keyboardRepository.findSliceBy(pageRequest);
		return keyboardResponseSlice;
	}

	/**
	 * 주변기기 - 키보드, 모니터, 마우스, 프린터, 기타
	 * 목록조회
	 */
	public Slice<PeripheralResponseDto> peripheralList(String category, int pages) {
		PageRequest pageRequest = PageRequest.of(pages, 30, Sort.by(Sort.Direction.ASC, "id"));

		Slice<PeripheralResponseDto> peripheralResponseDtoSlice = null;

		if(category.equals("keyboard")){
			peripheralResponseDtoSlice =  keyboardRepository.findSliceBy(pageRequest);
		} else if(category.equals("monitor")){
			peripheralResponseDtoSlice =  monitorRepository.findSliceBy(pageRequest);
		} else if(category.equals("mouse")){
			peripheralResponseDtoSlice =  mouseRepository.findSliceBy(pageRequest);
		} else if(category.equals("printer")){
			peripheralResponseDtoSlice =  printerRepository.findSliceBy(pageRequest);
		} else if(category.equals("etc")){
			peripheralResponseDtoSlice =  monitorRepository.findSliceBy(pageRequest);
		}

		return peripheralResponseDtoSlice;
	}
}
