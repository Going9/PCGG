package com.ssafy.pcgg.domain.peripheral.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.pcgg.domain.peripheral.entity.PeripheralReview;

@Repository
public interface PeripheralReviewRepository extends JpaRepository<PeripheralReview, Long> {

	@Query("SELECT AVG(pr.rating) FROM PeripheralReview pr WHERE pr.peripheralTypeNs.name = :typeName AND pr.peripheralId = :peripheralId")
	Double findAverageRatingByTypeNameAndPeripheralId(@Param("typeName") String typeName, @Param("peripheralId") Long peripheralId);

	@Query("SELECT pr FROM PeripheralReview pr WHERE pr.peripheralTypeNs.name = :typeName AND pr.peripheralId = :peripheralId")
	Slice<PeripheralReview> findSliceByTypeNameAndPeripheralId(@Param("typeName") String typeName, @Param("peripheralId") Long peripheralId, Pageable pageable);
}
