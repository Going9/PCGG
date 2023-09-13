package com.ssafy.pcgg.domain.peripheral.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.pcgg.domain.peripheral.dto.PeripheralResponseDto;
import com.ssafy.pcgg.domain.peripheral.entity.Monitor;

public interface MonitorRepository  extends JpaRepository<Monitor, Long> {
	Slice<PeripheralResponseDto> findSliceBy(Pageable pageable);
}
