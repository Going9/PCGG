package com.ssafy.pcgg.domain.peripheral.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.peripheral.entity.PeripheralRating;
import com.ssafy.pcgg.domain.peripheral.entity.PeripheralTypeNs;

@Repository
public interface PeripheralRatingRepository extends JpaRepository<PeripheralRating, Long> {
}
