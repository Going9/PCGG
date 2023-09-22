package com.ssafy.pcgg.domain.usedmarket.service;

import com.ssafy.pcgg.domain.auth.UserIdDto;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketCreateDto;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketListDto;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketPostDto;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketUpdateDto;
import com.ssafy.pcgg.domain.usedmarket.entity.UsedMarket;
import com.ssafy.pcgg.domain.usedmarket.exception.InvalidUserException;
import com.ssafy.pcgg.domain.usedmarket.exception.UsedMarketNotFoundException;
import com.ssafy.pcgg.domain.usedmarket.repository.UsedMarketRepository;
import com.ssafy.pcgg.domain.user.UserEntity;
import com.ssafy.pcgg.domain.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsedMarketService {

  @Autowired
  private UsedMarketRepository usedMarketRepository;

  @Autowired
  private UserRepository userRepository;

  @Transactional
  public Long createUsedMarketPost(UserIdDto userId, UsedMarketCreateDto usedMarketCreateDto) {
    UserEntity user = userRepository.findById(userId.getUserId()).orElseThrow(()
        -> new InvalidUserException("사용자가 아닙니다."));
    return usedMarketRepository.save(usedMarketCreateDto.toEntity(user)).getId();
  }

  @Transactional
  public void deleteUsedMarketPost(UserIdDto userId, Long usedMarketId)  {
      UsedMarket usedMarket = usedMarketRepository.findById(usedMarketId).orElseThrow(()
          -> new UsedMarketNotFoundException("해당 게시글이 없습니다."));
      if (!Objects.equals(usedMarket.getUser().getUserId(), userId.getUserId())) {
        throw new InvalidUserException("작성자만 삭제할 수 있습니다.");
    }
      usedMarketRepository.delete(usedMarket);
  }

  @Transactional
  public void updateUsedMarketPost(UserIdDto userId, Long usedMarketId, UsedMarketUpdateDto usedMarketUpdateDto) {

    UsedMarket usedMarket = usedMarketRepository.findById(usedMarketId).orElseThrow(()
          -> new UsedMarketNotFoundException("해당 게시글이 없습니다."));

    if (!Objects.equals(usedMarket.getUser().getUserId(), userId.getUserId())) {
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

  @Transactional(readOnly = true)
  public List<UsedMarketListDto> getUsedMarketPosts(int page, int size, String keyword) {
    Pageable pageable = Pageable.ofSize(size).withPage(page - 1);
    List<UsedMarket> usedMarketList = usedMarketRepository.findAllByTitleContainingIgnoreCaseOrderByIdDesc(keyword, pageable);
    List<UsedMarketListDto> usedMarketListDtos = new ArrayList<>();
    for (UsedMarket tmp : usedMarketList) {
      UsedMarketListDto usedMarketListDto = new UsedMarketListDto(tmp);
      usedMarketListDtos.add(usedMarketListDto);
    }
    return usedMarketListDtos;


  }
}
