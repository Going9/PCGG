package com.ssafy.pcgg.domain.usedmarket.dto;

import com.ssafy.pcgg.domain.usedmarket.entity.UsedMarket;
import com.ssafy.pcgg.domain.usedmarket.entity.UsedMarketComment;
import com.ssafy.pcgg.domain.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsedMarketCommentCreateDto {

    private Integer reStep;
    private Integer ref;
    private Boolean isReply;
    private String content;

    public UsedMarketComment toEntity(UserEntity user, UsedMarket usedMarket) {
        return UsedMarketComment.builder()
                .reStep(this.reStep)
                .ref(this.ref)
                .isReply(this.isReply)
                .content(this.content)
                .user(user)
                .usedMarket(usedMarket)
                .build();
    }
}
