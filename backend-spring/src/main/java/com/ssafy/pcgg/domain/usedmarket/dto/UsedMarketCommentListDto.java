package com.ssafy.pcgg.domain.usedmarket.dto;

import com.ssafy.pcgg.domain.usedmarket.entity.UsedMarketComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsedMarketCommentListDto {

    private Long id;
    private Boolean isReply;
    private String content;
    private Integer ref;
    private Integer reStep;

    public UsedMarketCommentListDto(UsedMarketComment usedMarketComment)  {
        this.id = usedMarketComment.getId();
        this.isReply = usedMarketComment.getIsReply();
        this.content = usedMarketComment.getContent();
        this.ref = usedMarketComment.getRef();
        this.reStep = usedMarketComment.getReStep();
    }
}
