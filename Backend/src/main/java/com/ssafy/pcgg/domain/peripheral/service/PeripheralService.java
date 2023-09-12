package com.ssafy.pcgg.domain.peripheral.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ssafy.pcgg.domain.peripheral.dto.KeyboardResponse;
import com.ssafy.pcgg.domain.peripheral.entity.Keyboard;
import com.ssafy.pcgg.domain.peripheral.repository.KeyboardRepository;

@RequiredArgsConstructor
@Service
public class PeripheralService {

	private final KeyboardRepository keyboardRepository;

	/**
	* 주변기기 - 키보드
	* 목록조회
	*/
	public Slice<KeyboardResponse> keyboardList(int pages) {
		PageRequest pageRequest = PageRequest.of(pages, 5, Sort.by(Sort.Direction.ASC, "id"));
		Slice<KeyboardResponse> keyboardResponseSlice =  keyboardRepository.findSliceBy(pageRequest);
		return keyboardResponseSlice;
	}
}
