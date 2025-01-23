package com.ott.onde.post.controller;


import com.ott.onde.post.dto.CommentResponseDto;
import com.ott.onde.post.dto.LikesRequestsDto;
import com.ott.onde.post.dto.PostResponseDto;
import com.ott.onde.post.service.LikesService;
import com.ott.onde.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class LikesController {
    private final LikesService likesService;

    // 게시글 좋아요
    @PutMapping("/like/{postIdx}")
    public PostResponseDto likePost(@PathVariable Long postIdx, @RequestBody LikesRequestsDto requestsDto) {
        return likesService.likePost(postIdx, requestsDto);
    }

    // 댓글 좋아요
    @PutMapping("/comment/like/{commentIdx}")
    public CommentResponseDto likeComment(@PathVariable Long commentIdx, @RequestBody LikesRequestsDto requestsDto) {
        return likesService.likeComment(commentIdx, requestsDto);
    }

}
