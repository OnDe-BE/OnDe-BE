package com.ott.reelpick.post.controller;

import com.ott.reelpick.post.dto.PostRequestsDto;
import com.ott.reelpick.post.dto.PostResponseDto;
import com.ott.reelpick.post.dto.SuccessResponseDto;
import com.ott.reelpick.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class PostController {

    private final PostService postService;

    // 게시판 조회
    @GetMapping("/{boardId}")
    public List<PostResponseDto> getPosts(@PathVariable Integer boardId) {
        return postService.getPosts(boardId);
    }

    //게시글 작성
    @PostMapping("/create")
    public PostResponseDto createPost(@RequestBody PostRequestsDto requestsDto) {
        return postService.createPost(requestsDto);
    }

    //게시글 상세조회
    @GetMapping("/details/{postIdx}")
    public PostResponseDto getPost(@PathVariable Long postIdx) {
        return postService.getPost(postIdx);
    }

    //게시글 수정
    @PutMapping("/modify/{postIdx}")
    public PostResponseDto updatePost(@PathVariable Long postIdx, @RequestBody PostRequestsDto requestsDto) throws Exception {
        return postService.updatePost(postIdx, requestsDto);
    }

    //게시글 삭제 - 테스트 필요
    @DeleteMapping("/delete/{postIdx}")
    public SuccessResponseDto deletePost(@PathVariable Long postIdx, @RequestBody PostRequestsDto requestsDto) throws Exception {
        return postService.deletePost(postIdx, requestsDto);
    }



}