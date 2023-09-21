package com.ssafy.pcgg.domain.usedmarket.dto;

import com.ssafy.pcgg.domain.usedmarket.entity.UsedMarket;
import com.ssafy.pcgg.domain.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsedMarketUpdateDto {

  private String title;
  private String content;
  private Integer price;
  private String summary;

  public UsedMarket toEntity(UserEntity user) {
    return UsedMarket.builder()
        .title(this.title)
        .content(this.content)
        .summary(this.summary)
        .price(this.price)
        .user(user)
        .build();
  }
}
