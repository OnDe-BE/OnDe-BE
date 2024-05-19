package com.ott.reelpick.post.controller;


import com.ott.reelpick.post.dto.CommentResponseDto;
import com.ott.reelpick.post.dto.PostResponseDto;
import com.ott.reelpick.post.service.LikesService;
import com.ott.reelpick.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class LikesController {
    private final LikesService likesService;

    // 게시글 좋아요
    @PutMapping("/like/{postIdx}")
    public PostResponseDto likePost(@PathVariable Long postIdx, User user) {
        return likesService.likePost(postIdx, user);
    }

    // 댓글 좋아요
    @PutMapping("/comment/like/{commentIdx}")
    public CommentResponseDto likeComment(@PathVariable Long commentIdx, User user) {
        return likesService.likeComment(commentIdx, user);
    }

}
