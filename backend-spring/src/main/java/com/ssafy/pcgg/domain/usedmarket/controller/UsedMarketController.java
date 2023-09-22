package com.ssafy.pcgg.domain.usedmarket.controller;

import com.ssafy.pcgg.domain.auth.CurrentUserId;
import com.ssafy.pcgg.domain.auth.UserIdDto;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketCreateDto;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketListDto;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketPostDto;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketUpdateDto;
import com.ssafy.pcgg.domain.usedmarket.service.UsedMarketService;
import com.ssafy.pcgg.domain.user.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/used-market")
public class UsedMarketController {

  private final UsedMarketService usedMarketService;

  @PostMapping
  @CurrentUserId("userId")
  public ResponseEntity<Long> createUsedMarketPost(UserIdDto userId, HttpServletRequest request, @RequestBody UsedMarketCreateDto usedMarketCreateDto) {
    Long result = usedMarketService.createUsedMarketPost(userId, usedMarketCreateDto);
    return ResponseEntity.status(201).body(result);
  }

  @DeleteMapping("/{usedMarketId}")
  @CurrentUserId("userId")
  public ResponseEntity<Void> deleteUsedMarket(UserIdDto userId, HttpServletRequest request, @PathVariable Long usedMarketId) {
    usedMarketService.deleteUsedMarketPost(userId, usedMarketId);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{usedMarketId}")
  @CurrentUserId("userId")
  public ResponseEntity<Long> updateUsedMarketPost(UserIdDto userId, HttpServletRequest request, @PathVariable Long usedMarketId, @RequestBody UsedMarketUpdateDto usedMarketUpdateDto) {
    usedMarketService.updateUsedMarketPost(userId, usedMarketId, usedMarketUpdateDto);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{usedMarketId}")
  @CurrentUserId("userIdDto")
  public ResponseEntity<UsedMarketPostDto> getUsedMarketPost(@PathVariable Long usedMarketId) {
    UsedMarketPostDto usedMarketPostDto = usedMarketService.getUsedMarketPost(usedMarketId);
    return ResponseEntity.ok(usedMarketPostDto);
  }

  @GetMapping
  public ResponseEntity<List<UsedMarketListDto>> getUsedMarketPosts(@RequestParam(value="page",
      defaultValue="1") int page, @RequestParam(value="size", defaultValue="15") int size, @RequestParam(value="keyword", defaultValue="") String keyword) {
      List<UsedMarketListDto> getTransactions = usedMarketService.getUsedMarketPosts(page, size, keyword);
      return ResponseEntity.ok(getTransactions);


  }
}
