package com.ott.onde.post.service;

import com.ott.onde.post.dto.PostRequestsDto;
import com.ott.onde.post.dto.PostResponseDto;
import com.ott.onde.post.dto.SuccessResponseDto;
import com.ott.onde.post.entity.BoardKind;
import com.ott.onde.post.entity.Post;
import com.ott.onde.post.repository.BoardKindRepository;
import com.ott.onde.post.repository.PostRepository;
import com.ott.onde.user.entity.User;
import com.ott.onde.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BoardKindRepository boardKindRepository;

    //게시글 전체 목록 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts(Integer boardId) {
        BoardKind boardkind = boardKindRepository.findAllByBoardid(boardId).get(0);
        return postRepository.findAllByBoardkind(boardkind).stream().map(PostResponseDto::new).toList();
    }

    //게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestsDto requestsDto) {
        User user = userRepository.findAllById(requestsDto.getId());
        BoardKind boardkind = boardKindRepository.findAllByBoardid(requestsDto.getBoardid()).get(0);
        Post post = new Post(requestsDto, boardkind, user);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    //게시글 상세조회
    @Transactional
    public PostResponseDto getPost(Long postIdx) {
        return postRepository.findById(postIdx).map(PostResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }

    //게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long postIdx, PostRequestsDto requestsDto) throws Exception {
        User user = userRepository.findAllById(requestsDto.getId());
        BoardKind boardkind = boardKindRepository.findAllByBoardid(requestsDto.getBoardid()).get(0);
        Post post = postRepository.findById(postIdx).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if(!requestsDto.getUser_idx().equals(post.getUser().getUserId()))
            throw new Exception("아이디가 일치하지 않습니다.");

        post.update(requestsDto, boardkind, user);
        postRepository.flush(); // responseDto 에 modified 업데이트를 위해 flush 사용

        return new PostResponseDto(post);
    }

    //게시글 삭제
    @Transactional
    public SuccessResponseDto deletePost(Long postIdx, PostRequestsDto requestsDto) throws Exception {
        Post post = postRepository.findById(postIdx).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        if (!requestsDto.getUser_idx().equals(post.getUser().getUserId()))
            throw new Exception("아이디가 일치하지 않습니다.");

        postRepository.deleteById(postIdx);
        return new SuccessResponseDto(true);
    }


    public void clear() {
        postRepository.deleteAll();
    }
}
