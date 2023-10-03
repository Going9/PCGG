package com.ssafy.pcgg.domain.usedmarket.entity;

import com.ssafy.pcgg.domain.BaseTimeEntity;
import com.ssafy.pcgg.domain.recommend.entity.ChassisEntity;
import com.ssafy.pcgg.domain.recommend.entity.CoolerEntity;
import com.ssafy.pcgg.domain.recommend.entity.CpuEntity;
import com.ssafy.pcgg.domain.recommend.entity.GpuEntity;
import com.ssafy.pcgg.domain.recommend.entity.MainboardEntity;
import com.ssafy.pcgg.domain.recommend.entity.PowerEntity;
import com.ssafy.pcgg.domain.recommend.entity.RamEntity;
import com.ssafy.pcgg.domain.recommend.entity.SsdEntity;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketUpdateDto;
import com.ssafy.pcgg.domain.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor
public class UsedMarket extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name="user_id")
  @ManyToOne
  private UserEntity user;

  private String title;

  @Column(columnDefinition="TEXT")
  private String content;

  private String summary;

  private Integer price;

  @ColumnDefault("false")
  private Boolean state;

  @Builder
  public UsedMarket(UserEntity user, String title, String content, String summary, Integer price, Boolean state, String usedMarketImage) {
    this.user = user;
    this.title = title;
    this.content = content;
    this.summary = summary;
    this.price = price;
    this.state = state;
  }

  public void complete() {
    this.state = true;
  }

  public void update(UsedMarketUpdateDto usedMarketUpdateDto) {
    this.title = usedMarketUpdateDto.getTitle();
    this.content = usedMarketUpdateDto.getContent();
    this.summary = usedMarketUpdateDto.getSummary();
    this.price = usedMarketUpdateDto.getPrice();
  }

}



