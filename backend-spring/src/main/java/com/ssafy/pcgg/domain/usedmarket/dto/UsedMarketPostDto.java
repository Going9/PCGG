package com.ssafy.pcgg.domain.usedmarket.dto;

import com.ssafy.pcgg.domain.DateTimeUtils;
import com.ssafy.pcgg.domain.usedmarket.entity.UsedMarket;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsedMarketPostDto {
  private Long id;
  private Long userId;
  private String title;
  private String content;
  private String summary;
  private String nickname;
  private Integer price;
  private Boolean state;
  private String createdAt;
  private String updatedAt;

  public UsedMarketPostDto(UsedMarket usedMarket) {
    this.id = usedMarket.getId();
    this.userId = usedMarket.getUser().getUserId();
    this.title = usedMarket.getTitle();
    this.content = usedMarket.getContent();
    this.summary = usedMarket.getSummary();
    this.nickname = usedMarket.getUser().getNickname();
    this.price = usedMarket.getPrice();
    this.state = usedMarket.getState();
    this.createdAt = DateTimeUtils.formatLocalDateTime(usedMarket.getCreatedAt());
    this.updatedAt = DateTimeUtils.formatLocalDateTime(usedMarket.getUpdatedAt());

  }

}
