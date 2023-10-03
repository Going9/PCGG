package com.ssafy.pcgg.domain.usedmarket.controller;

import com.ssafy.pcgg.domain.auth.CurrentUserId;
import com.ssafy.pcgg.domain.auth.UserIdDto;
import com.ssafy.pcgg.domain.usedmarket.dto.UsedMarketCommentCreateDto;
import com.ssafy.pcgg.domain.usedmarket.service.UsedMarketCommentService;
import com.ssafy.pcgg.domain.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/used-market")
public class UsedMarketCommentController {

    private final UsedMarketCommentService usedMarketCommentService;

    private final UserService userService;

    @PostMapping("/{usedMarketId}/comment")
    @CurrentUserId("userId")
    public ResponseEntity<Long> createUsedMarketComment(UserIdDto userId, HttpServletRequest request, @PathVariable Long usedMarketId, UsedMarketCommentCreateDto usedMarketCommentCreateDto) {
        Long result = usedMarketCommentService.createUsedMarketComment(userId, usedMarketId, usedMarketCommentCreateDto);
        return ResponseEntity.status(201).body(result);
    }


//    @DeleteMapping("/{usedMarketId}/comment/{usedMarketCommentId}")
//    @CurrentUserId("userId")
//    public ResponseEntity<Long> deleteUsedMarketComment(UserIdDto userID, HttpServletRequest request, @PathVariable Long usedMarketId, @PathVariable) {
//        Long result = usedMarketCommentService.deleteUsedMarketComment(userId, usedMarke);
//        return ResponseEntity.status(200).body(result);
//    }
//
//    @GetMapping("/{usedMarketId}/")
//    public ResponseEntity<List<UsedMarketCommentList>> getUsedMarketCommentList(@PathVariable Long usedMarketId) {
//        List<UsedMarketCommentListDto> getTransactions = usedMarketCommentService.getUsedMarketCommentList(usedMarketId);
//        return ResponseEntity.ok(getTransactions);
//
//    }


}
