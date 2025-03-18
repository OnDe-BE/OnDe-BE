package com.ott.onde.post.service;

import com.ott.onde.post.dto.PostRequestsDto;
import com.ott.onde.post.dto.PostResponseDto;
import com.ott.onde.post.dto.SuccessResponseDto;
import com.ott.onde.post.entity.Board;
import com.ott.onde.post.entity.Post;
import com.ott.onde.post.repository.BoardRepository;
import com.ott.onde.post.repository.CommentRepository;
import com.ott.onde.post.repository.PostRepository;
import com.ott.onde.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService{

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPosts(int boardId, int type, int nowPage, int pageSize) {
        // type = 0 : 인기순(조회순), type = 1 : 최신순, type = 2 : 좋아요순

        Sort sort = switch (type) {
            case 0 -> Sort.by(Sort.Direction.DESC, "postViews");
            case 1 -> Sort.by(Sort.Direction.DESC, "modifiedAt");
            case 2 -> Sort.by(Sort.Direction.DESC, "like_count");
            default -> Sort.by(Sort.Direction.DESC, "postViews");
        };

        PageRequest pageRequest = PageRequest.of(nowPage, pageSize, sort);

        Page<PostResponseDto> post = this.postRepository.findPostsByBoardId(pageRequest, Long.parseLong(Integer.toString(boardId)))
                .map(PostResponseDto::new);

        return post.map(x-> {
            Optional<Integer> count = this.commentRepository.findCountByPostId(x.getPostIdx());
            return x.setCommentCount(x, count.orElse(0));
        });
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getTopPosts(Integer parentId) {
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "like_count"));

        Page<PostResponseDto> post = this.postRepository.findTop3ByParentId(pageRequest, Long.parseLong(Integer.toString(parentId))).map(PostResponseDto::new);
        return post.map(x-> {
            Optional<Integer> count = this.commentRepository.findCountByPostId(x.getPostIdx());
            return x.setCommentCount(x, count.orElse(0));
        });
    }

    //게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestsDto requestsDto, User user) {
        Board board = boardRepository.findAllByBoardId(requestsDto.getBoardId()).get(0);
        Post post = new Post(requestsDto, board, user);
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
        Board board = boardRepository.findAllByBoardId(requestsDto.getBoardId()).get(0);
        Post post = postRepository.findById(postIdx).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if(!user.getUserCode().equals(post.getUser().getUserCode()))
            throw new Exception("아이디가 일치하지 않습니다.");

        post.update(requestsDto, board, user);
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
