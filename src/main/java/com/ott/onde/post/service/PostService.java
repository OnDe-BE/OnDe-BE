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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService{

    private final PostRepository postRepository;
    private final BoardKindRepository boardKindRepository;

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts(Integer boardId, Integer type) {
        // type = 0 : 인기순(조회순), type = 1 : 최신순, type = 2 : 좋아요순
        BoardKind boardKind = boardKindRepository.findAllByBoardId(boardId).get(0);

        List<Post> posts;

        // type이 null일 경우 최신순 정렬
        if (type == null || type == 1) {
            posts = postRepository.findAllByBoardKindOrderByCreatedAtDesc(boardKind);
        } else {
            switch (type) {
                case 0:
                    posts = postRepository.findAllByBoardKindOrderByPostViewsDesc(boardKind);
                    break;
                case 2:
                    posts = postRepository.findAllByBoardKindOrderByLikeCountDesc(boardKind);
                    break;
                default:
                    posts = postRepository.findAllByBoardKindOrderByCreatedAtDesc(boardKind); // 기본값: 최신순 정렬
            }
        }

        return posts.stream().map(PostResponseDto::new).toList();
    }

    //게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestsDto requestsDto, User user) {
        BoardKind boardKind = boardKindRepository.findAllByBoardId(requestsDto.getBoardId()).get(0);
        Post post = new Post(requestsDto, boardKind, user);
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
    public PostResponseDto updatePost(Long postIdx, PostRequestsDto requestsDto, User user) throws Exception {
        BoardKind boardKind = boardKindRepository.findAllByBoardId(requestsDto.getBoardId()).get(0);
        Post post = postRepository.findById(postIdx).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if(!user.getUserId().equals(post.getUser().getUserId()))
            throw new Exception("아이디가 일치하지 않습니다.");

        post.update(requestsDto, boardKind, user);
        postRepository.flush(); // responseDto 에 modified 업데이트를 위해 flush 사용

        return new PostResponseDto(post);
    }

    //게시글 삭제
    @Transactional
    public SuccessResponseDto deletePost(Long postIdx, User user) throws Exception {
        Post post = postRepository.findById(postIdx).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        if (!user.getUserId().equals(post.getUser().getUserId()))
            throw new Exception("아이디가 일치하지 않습니다.");

        postRepository.deleteById(postIdx);
        return new SuccessResponseDto(true);
    }


    public void clear() {
        postRepository.deleteAll();
    }


}
