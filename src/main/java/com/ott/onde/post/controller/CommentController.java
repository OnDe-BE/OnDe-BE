package com.ott.onde.post.controller;

import com.ott.onde.post.dto.CommentRequestDto;
import com.ott.onde.post.dto.CommentResponseDto;
import com.ott.onde.post.dto.SuccessResponseDto;
import com.ott.onde.post.service.CommentService;
import com.ott.onde.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/comment/*")
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{postIdx}")   // 여기서 ID는 게시글의 id
    public CommentResponseDto createComment(@PathVariable Long postIdx, @RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(postIdx, requestDto);
    }

    // 댓글 수정
    @PutMapping("/modify/{commentIdx}")    // 여기서 ID는 댓글의 id
    public CommentResponseDto updateComment(@PathVariable Long commentIdx, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(commentIdx, requestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/delete/{commentIdx}")     // 여기서 ID는 댓글의 id
    public SuccessResponseDto deleteComment(@PathVariable Long commentIdx, @RequestBody CommentRequestDto requestDto) {
        return commentService.deleteComment(commentIdx, requestDto);
    }

}
