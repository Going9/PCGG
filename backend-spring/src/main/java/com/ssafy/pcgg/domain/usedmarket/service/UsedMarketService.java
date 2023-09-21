package com.ssafy.pcgg.domain.usedmarket.service;

import com.ssafy.pcgg.domain.auth.CurrentUser;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketCreateDto;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketPostDto;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketUpdateDto;
import com.ssafy.pcgg.domain.usedmarket.entity.UsedMarket;
import com.ssafy.pcgg.domain.usedmarket.exception.InvalidUserException;
import com.ssafy.pcgg.domain.usedmarket.exception.UsedMarketNotFoundException;
import com.ssafy.pcgg.domain.usedmarket.repository.UsedMarketRepository;
import com.ssafy.pcgg.domain.user.UserEntity;
import java.util.Objects;
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
  public void deleteUsedMarketPost(Long usedMarketId, UserEntity user) {
      UsedMarket usedMarket = usedMarketRepository.findById(usedMarketId).orElseThrow(()
          -> new UsedMarketNotFoundException("해당 게시글이 없습니다."));
      if (!Objects.equals(usedMarket.getUser().getUserId(), user.getUserId())) {
        throw new InvalidUserException("작성자만 삭제할 수 있습니다.");
    }
      usedMarketRepository.delete(usedMarket);
  }

  @Transactional
  public void updateUsedMarketPost(Long usedMarketId, UsedMarketUpdateDto usedMarketUpdateDto,
      UserEntity user) {

    UsedMarket usedMarket = usedMarketRepository.findById(usedMarketId).orElseThrow(()
          -> new UsedMarketNotFoundException("해당 게시글이 없습니다."));

    if (!Objects.equals(usedMarket.getUser().getUserId(), user.getUserId())) {
      throw new InvalidUserException("작성자만 수정할 수 있습니다.");
    }
    usedMarket.update(usedMarketUpdateDto);
  }

  @Transactional(readOnly = true)
  public UsedMarketPostDto getUsedMarketPost(Long usedMarketId) {
    UsedMarket usedMarket = usedMarketRepository.findById(usedMarketId).orElseThrow(()
        -> new UsedMarketNotFoundException("해당 게시글이 없습니다."));
    UsedMarketPostDto usedMarketPostDto = null;
    usedMarketPostDto = new UsedMarketPostDto(usedMarket);

    return usedMarketPostDto;
  }
}
