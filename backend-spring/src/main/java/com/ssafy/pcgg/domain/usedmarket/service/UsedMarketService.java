package com.ssafy.pcgg.domain.usedmarket.service;

import com.ssafy.pcgg.domain.auth.CurrentUser;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketCreateDto;
import com.ssafy.pcgg.domain.usedmarket.entity.UsedMarket;
import com.ssafy.pcgg.domain.usedmarket.exception.UsedMarketException;
import com.ssafy.pcgg.domain.usedmarket.repository.UsedMarketRepository;
import com.ssafy.pcgg.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsedMarketService {

  @Autowired
  private UsedMarketRepository usedMarketRepository;

  @Transactional
  public Long createUsedMarketPost(UsedMarketCreateDto usedMarketCreateDto, @CurrentUser UserEntity user) {
    return usedMarketRepository.save(usedMarketCreateDto.toEntity(user)).getId();
  }

  @Transactional
  public void deleteUsedMarketService(Long usedMarketId) {
      UsedMarket usedMarket = usedMarketRepository.findById(usedMarketId).orElseThrow(()
          -> new UsedMarketException("해당 게시글이 없습니다."));
      usedMarketRepository.delete(usedMarket);
  }
}
