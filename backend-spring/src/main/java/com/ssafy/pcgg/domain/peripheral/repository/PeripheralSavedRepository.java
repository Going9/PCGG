package com.ssafy.pcgg.domain.peripheral.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.peripheral.entity.PeripheralSaved;

import java.util.List;

@Repository
public interface PeripheralSavedRepository extends JpaRepository<PeripheralSaved, Long> {
    List<PeripheralSaved> findByUser_UserIdAndPeripheralTypeNs_Name(Long userId, String category);
}
