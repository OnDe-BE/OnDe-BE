package com.ott.ond.post.service;

import com.ott.ond.post.dto.CommentRequestDto;
import com.ott.ond.post.dto.CommentResponseDto;
import com.ott.ond.post.dto.SuccessResponseDto;
import com.ott.ond.post.entity.Comment;
import com.ott.ond.post.entity.Post;
import com.ott.ond.post.repository.CommentRepository;
import com.ott.ond.post.repository.PostRepository;
import com.ott.ond.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    //댓글 작성
    @Transactional
    public CommentResponseDto createComment(Long postIdx, CommentRequestDto requestDto, User user){
        //선택한 게시글 DB 에서 조회
        Optional<Post> post = postRepository.findById(postIdx);
        if (post.isEmpty()) {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
        }
        Long parentId = requestDto.getParent_id();
        Comment comment = new Comment(requestDto, post.get(), user);

        if (parentId == null) {  // parent 가 없다면
            commentRepository.save(comment);    // 바로 저장
            return CommentResponseDto.from(comment);
        }

        // parentComment 가 있다면 parent comment 에 childComment 를 추가
        Comment parentComment = commentRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        parentComment.addChildComment(comment); // parentComment 에 childComment 추가
        commentRepository.save(comment);

        return CommentResponseDto.from(comment);

    }


    //댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentIdx, CommentRequestDto requestDto, User user){
        // 선택한 댓글이 DB에 있는지 확인
        Optional<Comment> comment = commentRepository.findById(commentIdx);
        if (comment.isEmpty()) {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
        }

        // 댓글의 작성자와 수정하려는 사용자의 정보가 일치하는지 확인
        // 관리자의 수정 기능은 이후에 추가할 것
        Optional<Comment> found = commentRepository.findByCommentIdxAndUser(commentIdx, user);
        if (found.isEmpty()) {
            throw new IllegalArgumentException("댓글 작성자가 아닙니다.");
        }

        // 댓글의 작성자와 수정하려는 사용자의 정보가 일치한다면, 댓글 수정
        comment.get().update(requestDto, user);
        commentRepository.flush();   // responseDto 에 modifiedAt 업데이트 해주기 위해 saveAndFlush 사용

        return CommentResponseDto.from(comment.get());

    }


    //댓글 삭제
    @Transactional
    public SuccessResponseDto deleteComment(Long commentIdx, User user){
        // 선택한 댓글이 DB에 있는지 확인
        Optional<Comment> comment = commentRepository.findById(commentIdx);
        if (comment.isEmpty()) {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
        }

        // 댓글의 작성자와 수정하려는 사용자의 정보가 일치하는지 확인
        // 관리자의 삭제 기능은 이후에 추가할 것
        Optional<Comment> found = commentRepository.findByCommentIdxAndUser(commentIdx, user);
        if (found.isEmpty()) {
            throw new IllegalArgumentException("댓글 작성자가 아닙니다.");
        }
        // 댓글의 작성자와 삭제하려는 사용자의 정보가 일치한다면, 댓글 삭제
        commentRepository.deleteById(commentIdx);
        return new SuccessResponseDto(true);
    }


}
