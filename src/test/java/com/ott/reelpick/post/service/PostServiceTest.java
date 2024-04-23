package com.ott.reelpick.post.service;

import com.ott.reelpick.post.dto.PostRequestsDto;
import com.ott.reelpick.post.dto.PostResponseDto;
import com.ott.reelpick.post.service.PostService;
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
    private PostService postService;

    @BeforeEach
    public void Reset(){
        postService.clear();
    }

    @Test
    @DisplayName("Post 생성 및 검색 테스트")
    void CreatePostAndGetPost(){
        //given
        PostRequestsDto postRequests1 = new PostRequestsDto();
        postRequests1.setUser_idx(1234L);
        postRequests1.setContents("본문1");
        postRequests1.setTitle("제목1");
        postRequests1.setBoard_id(1);

        PostRequestsDto postRequests2 = new PostRequestsDto();
        postRequests2.setUser_idx(1235L);
        postRequests2.setContents("본문2");
        postRequests2.setTitle("제목2");
        postRequests2.setBoard_id(1);

        //when
        postService.createPost(postRequests1);
        postService.createPost(postRequests2);

        List<PostResponseDto> test = postService.getPosts(1);
        PostResponseDto p = postService.getPost(Long.valueOf(1));

        //then
        assertThat(test.get(1).getContents()).isEqualTo("본문2");
        assertThat(test.get(1).getUser_idx()).isEqualTo(1235L);
        assertThat(p.getContents()).isEqualTo("본문1");
    }



}
