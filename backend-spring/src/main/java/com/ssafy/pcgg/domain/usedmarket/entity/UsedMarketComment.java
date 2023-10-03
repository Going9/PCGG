package com.ssafy.pcgg.domain.usedmarket.entity;

import com.ssafy.pcgg.domain.BaseTimeEntity;
import com.ssafy.pcgg.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor
public class UsedMarketComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "used_market_id")
    @ManyToOne
    private UsedMarket usedMarket;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity user;

    private String content;

    @ColumnDefault("false")
    private Boolean isReply;

    private Integer ref;

    private Integer reStep;

    @Builder
    public UsedMarketComment(UserEntity user, UsedMarket usedMarket, String content, Boolean isReply, Integer ref, Integer reStep) {
        this.user = user;
        this.usedMarket = usedMarket;
        this.content = content;
        this.isReply = isReply;
        this.ref = ref;
        this.reStep = reStep;
    }

}
