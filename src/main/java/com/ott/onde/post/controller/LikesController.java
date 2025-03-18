package com.ott.onde.post.controller;


import com.ott.onde.post.dto.CommentResponseDto;
import com.ott.onde.post.dto.LikesRequestsDto;
import com.ott.onde.post.dto.PostResponseDto;
import com.ott.onde.post.service.LikesService;
import com.ott.onde.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class LikesController {
    private final LikesService likesService;

    // 게시글 좋아요
    @PutMapping("/like/{postIdx}")
    public PostResponseDto likePost(@PathVariable Long postIdx, @AuthenticationPrincipal User user) {
        return likesService.likePost(postIdx, user);
    }

    // 댓글 좋아요
    @PutMapping("/comment/like/{commentIdx}")
    public CommentResponseDto likeComment(@PathVariable Long commentIdx, @AuthenticationPrincipal User user) {
        return likesService.likeComment(commentIdx, user);
    }

}
