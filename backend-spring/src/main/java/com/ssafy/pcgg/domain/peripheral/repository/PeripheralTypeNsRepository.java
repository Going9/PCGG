package com.ssafy.pcgg.domain.peripheral.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.peripheral.entity.PeripheralTypeNs;

@Repository
public interface PeripheralTypeNsRepository extends JpaRepository<PeripheralTypeNs, String> {
	Optional<PeripheralTypeNs> findByName(String typeName);
}
