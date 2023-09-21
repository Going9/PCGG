package com.ssafy.pcgg.domain.usedmarket.service;

import com.ssafy.pcgg.domain.auth.CurrentUser;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketCreateDto;
import com.ssafy.pcgg.domain.usedmarket.repository.UsedMarketRepository;
import com.ssafy.pcgg.domain.user.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsedMarketService {

  @Autowired
  private UsedMarketRepository usedMarketRepository;

  @Transactional
  public Long createUsedMarketPost(UsedMarketCreateDto usedMarketCreateDto, @CurrentUser UserEntity user) {
    return usedMarketRepository.save(usedMarketCreateDto.toEntity(user)).getId();
  }
}
