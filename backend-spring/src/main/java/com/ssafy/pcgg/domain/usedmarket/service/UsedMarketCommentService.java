package com.ssafy.pcgg.domain.usedmarket.service;

import com.ssafy.pcgg.domain.auth.UserIdDto;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketCommentCreateDto;
import com.ssafy.pcgg.domain.usedmarket.entity.UsedMarket;
import com.ssafy.pcgg.domain.usedmarket.exception.InvalidUserException;
import com.ssafy.pcgg.domain.usedmarket.exception.UsedMarketNotFoundException;
import com.ssafy.pcgg.domain.usedmarket.repository.UsedMarketCommentRepository;
import com.ssafy.pcgg.domain.usedmarket.repository.UsedMarketRepository;
import com.ssafy.pcgg.domain.user.UserEntity;
import com.ssafy.pcgg.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsedMarketCommentService {

    @Autowired
    private UsedMarketCommentRepository usedMarketCommentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsedMarketRepository usedMarketRepository;

    public Long createUsedMarketComment(UserIdDto userId, Long usedMarketId, UsedMarketCommentCreateDto usedMarketCommentCreateDto) {
        UserEntity user = userRepository.findById(userId.getUserId()).orElseThrow(()
           -> new InvalidUserException("사용자가 아닙니다."));

        UsedMarket usedMarket = usedMarketRepository.findById(usedMarketId).orElseThrow(()
           -> new UsedMarketNotFoundException("게시글이 없습니다."));
        return usedMarketCommentRepository.save(usedMarketCommentCreateDto.toEntity(user, usedMarket)).getId();
    }
}
