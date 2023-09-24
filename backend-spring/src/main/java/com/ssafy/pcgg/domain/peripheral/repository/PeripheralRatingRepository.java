package com.ssafy.pcgg.domain.peripheral.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.peripheral.entity.PeripheralRating;
import com.ssafy.pcgg.domain.peripheral.entity.PeripheralTypeNs;

@Repository
public interface PeripheralRatingRepository extends JpaRepository<PeripheralRating, Long> {

	@Query("SELECT AVG(pr.rating) FROM PeripheralRating pr WHERE pr.peripheralTypeNs.name = :typeName AND pr.peripheralId = : peripheralId")
	Double findAverageRatingByTypeNameAndPeripheralId(@Param("typeName") String typeName, @Param("peripheralId") Long peripheralId);
}
