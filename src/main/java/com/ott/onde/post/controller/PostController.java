package com.ott.onde.post.controller;

import com.ott.onde.post.dto.PostRequestsDto;
import com.ott.onde.post.dto.PostResponseDto;
import com.ott.onde.post.dto.SuccessResponseDto;
import com.ott.onde.post.entity.BoardKind;
import com.ott.onde.post.repository.BoardKindRepository;
import com.ott.onde.post.repository.PostRepository;
import com.ott.onde.post.service.PostService;
import com.ott.onde.security.UserDetailsImpl;
import com.ott.onde.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/")
public class PostController {

    private final PostService postService;

    // 게시판 조회
    @GetMapping("/{boardId}")
    public List<PostResponseDto> getPosts(@PathVariable Integer boardId) {
        return postService.getPosts(boardId);
    }

    //게시글 작성
    @PostMapping("/create")
    public PostResponseDto createPost(@RequestBody PostRequestsDto requestsDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(requestsDto, userDetails.getUser());
    }

    //게시글 상세조회
    @GetMapping("/details/{postIdx}")
    public PostResponseDto getPost(@PathVariable Long postIdx) {
        return postService.getPost(postIdx);
    }

    //게시글 수정
    @PutMapping("/modify/{postIdx}")
    public PostResponseDto updatePost(@PathVariable Long postIdx, @RequestBody PostRequestsDto requestsDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        return postService.updatePost(postIdx, requestsDto, userDetails.getUser());
    }

    //게시글 삭제
    @DeleteMapping("/delete/{postIdx}")
    public SuccessResponseDto deletePost(@PathVariable Long postIdx, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        return postService.deletePost(postIdx, userDetails.getUser());
    }



}