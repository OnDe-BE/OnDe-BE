package com.ott.ond.post.service;

import com.ott.ond.post.dto.CommentResponseDto;
import com.ott.ond.post.dto.PostResponseDto;
import com.ott.ond.post.entity.Comment;
import com.ott.ond.post.entity.Likes;
import com.ott.ond.post.entity.Post;
import com.ott.ond.post.repository.CommentRepository;
import com.ott.ond.post.repository.LikesRepository;
import com.ott.ond.post.repository.PostRepository;
import com.ott.ond.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 게시글 좋아요 기능
    public PostResponseDto likePost(Long postIdx, User user) {
        // 선택한 게시글이 DB에 있는지 확인
        Optional<Post> post = postRepository.findById(postIdx);
        if (post.isEmpty()) {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
        }

        // 이전에 좋아요 누른 적 있는지 확인
        Optional<Likes> found = likesRepository.findByPostAndUser(post.get(), user);
        if (found.isEmpty()) {  // 좋아요 누른적 없음
            Likes likes = Likes.of(post.get(), user);
            likesRepository.save(likes);
        } else { // 좋아요 누른 적 있음
            likesRepository.delete(found.get()); // 좋아요 눌렀던 정보를 지운다.
            likesRepository.flush();
        }

        return PostResponseDto.from(post.get());

    }

    // 댓글 좋아요 기능
    public CommentResponseDto likeComment(Long commentIdx, User user) {
        // 선택한 댓글이 DB에 있는지 확인
        Optional<Comment> comment = commentRepository.findById(commentIdx);
        if (comment.isEmpty()) {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
        }

        // 이전에 좋아요 누른 적 있는지 확인
        Optional<Likes> found = likesRepository.findByCommentAndUser(comment.get(), user);
        if (found.isEmpty()) {  // 좋아요 누른적 없음
            Likes likes = Likes.of(comment.get(), user);
            likesRepository.save(likes);
        } else { // 좋아요 누른 적 있음
            likesRepository.delete(found.get()); // 좋아요 눌렀던 정보를 지운다.
            likesRepository.flush();
        }

        return CommentResponseDto.from(comment.get());

    }
}
