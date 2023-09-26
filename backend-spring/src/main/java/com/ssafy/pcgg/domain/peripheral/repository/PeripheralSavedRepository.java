package com.ssafy.pcgg.domain.peripheral.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.peripheral.entity.PeripheralSaved;

@Repository
public interface PeripheralSavedRepository extends JpaRepository<PeripheralSaved, Long> {
}
