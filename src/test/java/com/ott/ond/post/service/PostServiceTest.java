package com.ott.ond.post.service;

import com.ott.ond.post.dto.PostRequestsDto;
import com.ott.ond.post.dto.PostResponseDto;
import com.ott.ond.post.repository.PostRepository;
import com.ott.ond.post.service.PostService;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    //@BeforeEach
    //public void Reset(){
        //postService.clear();
    //}

    @Test
    @DisplayName("Post 생성 및 검색 테스트")
    void CreatePostAndGetPost(){
        //given
        PostRequestsDto postRequests1 = new PostRequestsDto();
        postRequests1.setUser_idx(1234L);
        postRequests1.setContents("본문1");
        postRequests1.setTitle("제목1");
        postRequests1.setBoardid(1);

        PostRequestsDto postRequests2 = new PostRequestsDto();
        postRequests2.setUser_idx(1235L);
        postRequests2.setContents("본문2");
        postRequests2.setTitle("제목2");
        postRequests2.setBoardid(1);

        //when
        //postService.createPost(postRequests1);
        //postService.createPost(postRequests2);

        List<PostResponseDto> test = postService.getPosts(1);
        PostResponseDto p = postService.getPost(Long.valueOf(1));

        //then
        assertThat(test.get(1).getContents()).isEqualTo("본문2");
        assertThat(test.get(1).getUser_idx()).isEqualTo(1235L);
        assertThat(p.getContents()).isEqualTo("본문1");
    }

    @Test
    @DisplayName("Post 업데이트 및 삭제 테스트")
    void UpdatePost() throws Exception {
        //given
        PostRequestsDto postRequests1 = new PostRequestsDto();
        postRequests1.setUser_idx(1234L);
        postRequests1.setContents("본문업데이트1");
        postRequests1.setTitle("제목업데이트1");
        postRequests1.setBoardid(2);
        Long postIdx1 = Long.valueOf(1);

        PostRequestsDto postRequests2 = new PostRequestsDto();
        postRequests2.setUser_idx(1236L); //다른 사람이 수정하려고 할때는 업데이트되지 않아야함
        postRequests2.setContents("본문업데이트2");
        postRequests2.setTitle("제목업데이트2");
        postRequests2.setBoardid(1);
        Long postIdx2 = Long.valueOf(2);

        //when
        //PostResponseDto p1 = postService.updatePost(postIdx1, postRequests1);
        //PostResponseDto p2 = postService.updatePost(postIdx2, postRequests2);

        //then
        //assertThat(p1.getContents()).isEqualTo("본문업데이트1");
        //assertThat(p2.getContents()).isEqualTo("본문2");


    }



}
