package com.ssafy.pcgg.domain.usedmarket.controller;

import com.ssafy.pcgg.domain.auth.CurrentUser;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketCreateDto;
import com.ssafy.pcgg.domain.usedmarket.service.UsedMarketService;
import com.ssafy.pcgg.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/used-market")
public class UsedMarketController {

  private final UsedMarketService usedMarketService;

  @PostMapping
  public ResponseEntity<Long> createUsedMarketPost(@RequestBody UsedMarketCreateDto usedMarketCreateDto, @CurrentUser UserEntity user) {
    Long result = usedMarketService.createUsedMarketPost(usedMarketCreateDto, user);
    return ResponseEntity.status(201).body(result);
  }
}
