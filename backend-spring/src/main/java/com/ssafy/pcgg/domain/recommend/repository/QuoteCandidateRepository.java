package com.ssafy.pcgg.domain.recommend.repository;

import com.ssafy.pcgg.domain.recommend.entity.QuoteCandidateEntity;
import com.ssafy.pcgg.domain.part.entity.UsageNsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteCandidateRepository extends JpaRepository<QuoteCandidateEntity,Long>{

    void deleteByUsage(UsageNsEntity usage);

    @Query("select q " +
            "from QuoteCandidateEntity q " +
            "where q.usage = :usage " +
            "order by q.benchScore/q.totalPrice desc " +
            "limit 10 ")
    List<QuoteCandidateEntity> findAllMostPriceEfficientByUsage(@Param("usage")UsageNsEntity usage);

    @Query("select q " +
            "from QuoteCandidateEntity q " +
            "where q.usage = :usage " +
            "order by q.totalPrice " +
            "limit 10 ")
    List<QuoteCandidateEntity> findAllCheapestByUsage(@Param("usage") UsageNsEntity usage);

    @Query("select q " +
            "from QuoteCandidateEntity q " +
            "where q.usage = :usage " +
            "order by q.benchScore desc " +
            "limit 10 ")
    List<QuoteCandidateEntity> findAllBestPerformanceByUsage(@Param("usage") UsageNsEntity usage);

    @Query("select q " +
            "from QuoteCandidateEntity q " +
            "where q.usage = :usage " +
            "order by q.benchScore/q.totalPrice desc " +
            "limit 1 ")
    QuoteCandidateEntity findMostPriceEfficientByUsage(@Param("usage")UsageNsEntity usage);

    @Query("select q " +
            "from QuoteCandidateEntity q " +
            "where q.usage = :usage " +
            "order by q.totalPrice " +
            "limit 1 ")
    QuoteCandidateEntity findCheapestByUsage(@Param("usage") UsageNsEntity usage);

    @Query("select q " +
            "from QuoteCandidateEntity q " +
            "where q.usage = :usage " +
            "order by q.benchScore desc " +
            "limit 1 ")
    QuoteCandidateEntity findBestPerformanceByUsage(@Param("usage") UsageNsEntity usage);

    List<QuoteCandidateEntity> findByUsage(UsageNsEntity usage);
}
