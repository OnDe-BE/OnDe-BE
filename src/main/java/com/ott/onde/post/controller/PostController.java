package com.ott.onde.post.controller;

import com.ott.onde.post.dto.PostRequestsDto;
import com.ott.onde.post.dto.PostResponseDto;
import com.ott.onde.post.dto.SuccessResponseDto;
import com.ott.onde.post.service.PostService;
import com.ott.onde.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/")
public class PostController {

    private final PostService postService;

    // 게시판 조회 및 정렬
    @GetMapping("/category")
    public Page<PostResponseDto> getPosts(
            @RequestParam(name = "boardId", required = false, defaultValue = "2") Integer boardId,
            @RequestParam(required = false, defaultValue = "1") Integer type,
            @RequestParam(required = false, defaultValue = "0") Integer nowPage,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize// 선택적 파라미터
    ) {
        return postService.getPosts(boardId, type, nowPage, pageSize);
    }

    @GetMapping("/top")
    public Page<PostResponseDto> getPostsByParentId(@RequestParam(name = "parentId", required = false, defaultValue = "1") Integer parentId,
                                                    @RequestParam(required = false) Integer type
    ) {
        return postService.getTopPosts(parentId, type);
    }


    //게시글 작성
    @PostMapping("/create")
    public PostResponseDto createPost(@RequestBody PostRequestsDto requestsDto, @AuthenticationPrincipal User user) throws Exception{
        return postService.createPost(requestsDto, user);
    }

    //게시글 상세조회
    @GetMapping("/details/{postIdx}")
    public PostResponseDto getPost(@PathVariable Long postIdx) {
        return postService.getPost(postIdx);
    }

    //게시글 수정
    @PutMapping("/modify/{postIdx}")
    public PostResponseDto updatePost(@PathVariable Long postIdx, @RequestBody PostRequestsDto requestsDto, @AuthenticationPrincipal User user) throws Exception {
        return postService.updatePost(postIdx, requestsDto, user);
    }

    //게시글 삭제
    @DeleteMapping("/delete/{postIdx}")
    public SuccessResponseDto deletePost(@PathVariable Long postIdx, @AuthenticationPrincipal User user) throws Exception {
        return postService.deletePost(postIdx, user);
    }



}