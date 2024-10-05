package com.ott.onde.post.service;

import com.ott.onde.post.dto.CommentResponseDto;
import com.ott.onde.post.dto.LikesRequestsDto;
import com.ott.onde.post.dto.PostResponseDto;
import com.ott.onde.post.entity.Comment;
import com.ott.onde.post.entity.Likes;
import com.ott.onde.post.entity.Post;
import com.ott.onde.post.repository.CommentRepository;
import com.ott.onde.post.repository.LikesRepository;
import com.ott.onde.post.repository.PostRepository;
import com.ott.onde.user.entity.User;
import com.ott.onde.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 게시글 좋아요 기능
    @Transactional
    public PostResponseDto likePost(Long postIdx, LikesRequestsDto requestsDto) {
        // 선택한 게시글이 DB에 있는지 확인
        Optional<Post> post = postRepository.findById(postIdx);
        User user = userRepository.findAllById(requestsDto.getId());

        if (post.isEmpty()) {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
        }

        // 이전에 좋아요 누른 적 있는지 확인
        Optional<Likes> found = likesRepository.findByPostAndUser(post.get(), user);
        if (found.isEmpty()) {  // 좋아요 누른적 없음
            Likes likes = Likes.of(post.get(), user);
            likesRepository.save(likes);
            post.get().setLikeCount(post.get().getLikeCount() + 1); // like_count 증가

        } else { // 좋아요 누른 적 있음
            likesRepository.delete(found.get()); // 좋아요 눌렀던 정보를 지운다.
            likesRepository.flush();
            post.get().setLikeCount(post.get().getLikeCount() - 1); // like_count 감소
        }

        return PostResponseDto.from(post.get());

    }

    // 댓글 좋아요 기능
    @Transactional
    public CommentResponseDto likeComment(Long commentIdx, LikesRequestsDto requestsDto) {
        // 선택한 댓글이 DB에 있는지 확인
        Optional<Comment> comment = commentRepository.findById(commentIdx);
        User user = userRepository.findAllById(requestsDto.getId());

        if (comment.isEmpty()) {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
        }

        // 이전에 좋아요 누른 적 있는지 확인
        Optional<Likes> found = likesRepository.findByCommentAndUser(comment.get(), user);
        if (found.isEmpty()) {  // 좋아요 누른적 없음
            Likes likes = Likes.of(comment.get(), user);
            likesRepository.save(likes);
            comment.get().setLikeCount(comment.get().getLikeCount() + 1); //like_count 증가
        } else { // 좋아요 누른 적 있음
            likesRepository.delete(found.get()); // 좋아요 눌렀던 정보를 지운다.
            likesRepository.flush();
            comment.get().setLikeCount(comment.get().getLikeCount() - 1); //like_count 증가
        }

        return CommentResponseDto.from(comment.get());

    }
}
