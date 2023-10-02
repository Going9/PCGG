package com.ssafy.pcgg.domain.peripheral.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.peripheral.dto.PeripheralResponseDto;
import com.ssafy.pcgg.domain.peripheral.entity.Etc;

@Repository
public interface EtcRepository  extends JpaRepository<Etc, Long> {
	Slice<PeripheralResponseDto> findSliceBy(Pageable pageable);
}
