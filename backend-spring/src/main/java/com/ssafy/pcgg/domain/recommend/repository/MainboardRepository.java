package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.MainboardEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainboardRepository extends JpaRepository<MainboardEntity, Long> {

    @Query("SELECT m " +
            "FROM MainboardEntity" +
            " m " +
            "WHERE m.size = :caseSize " +
                "AND m.socketInfo = :socketInfo " +
                "AND m.classColumn = :class " +
                "AND m.memorySpec = :memorySpec " +
                "AND CASE " +
                    "WHEN :pcieVer=3 THEN m.pcie3 " +
                    "WHEN :pcieVer=4 THEN m.pcie4 " +
                    "WHEN :pcieVer=5 THEN m.pcie5 " +
                "END")
    List<MainboardEntity> findByCaseSizeSocketAndClassAndMemoryAndPcie(
            @Param("caseSize")String caseSize,
            @Param("socketInfo")String socketInfo,
            @Param("class")Integer classColumn,
            @Param("memorySpec")String memorySpec,
            @Param("pcieVer")Integer pcieVer
    );

    Slice<MainboardEntity> findSliceByNameContaining(Pageable pageable, String keyword);

    List<MainboardEntity> findAllByClassColumn(int partClass);

    List<MainboardEntity> findAllByClassColumnAndPriceLessThanEqual(int partClass, int budget);
}
