package com.ott.onde.post.service;

import com.ott.onde.post.dto.PostRequestsDto;
import com.ott.onde.post.dto.PostResponseDto;
import com.ott.onde.post.dto.SuccessResponseDto;
import com.ott.onde.post.entity.BoardKind;
import com.ott.onde.post.entity.Post;
import com.ott.onde.post.repository.BoardKindRepository;
import com.ott.onde.post.repository.CommentRepository;
import com.ott.onde.post.repository.PostRepository;
import com.ott.onde.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService{

    private final PostRepository postRepository;
    private final BoardKindRepository boardKindRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts(Integer boardId, Integer type) {
        // type = 0 : 인기순(조회순), type = 1 : 최신순, type = 2 : 좋아요순
        BoardKind boardKind = boardKindRepository.findAllByBoardId(boardId).get(0);

        List<Post> posts;

        // type이 null일 경우 최신순 정렬
        if (type == null || type == 1) {
            posts = postRepository.findAllByBoardKindOrderByCreatedAtDesc(boardKind);
        } else {
            posts = switch (type) {
                case 0 -> postRepository.findAllByBoardKindOrderByPostViewsDesc(boardKind);
                case 2 -> postRepository.findAllByBoardKindOrderByLikeCountDesc(boardKind);
                default -> postRepository.findAllByBoardKindOrderByCreatedAtDesc(boardKind); // 기본값: 최신순 정렬
            };
        }

        List<PostResponseDto> post = posts.stream().map(PostResponseDto::new).toList();

        return post.stream().map(x-> {
            Optional<Integer> count = this.commentRepository.findCountByPostId(x.getPostIdx());
            return x.setCommentCount(x, count.orElse(0));
        }).toList();
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
        if(!user.getUserCode().equals(post.getUser().getUserCode()))
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

        if (!user.getUserCode().equals(post.getUser().getUserCode()))
            throw new Exception("아이디가 일치하지 않습니다.");

        postRepository.deleteById(postIdx);
        return new SuccessResponseDto(true);
    }


    public void clear() {
        postRepository.deleteAll();
    }


}
