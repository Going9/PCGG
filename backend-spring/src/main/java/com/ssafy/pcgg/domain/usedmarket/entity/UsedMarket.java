package com.ssafy.pcgg.domain.usedmarket.entity;

import com.ssafy.pcgg.domain.BaseTimeEntity;
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
  public UsedMarket(UserEntity user, String title, String content, String summary, Integer price, Boolean state) {
    this.user = user;
    this.title = title;
    this.content = content;
    this.summary = summary;
    this.price = price;
    this.state = state;

  }





}
