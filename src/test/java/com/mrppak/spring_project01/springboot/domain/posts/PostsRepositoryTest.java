package com.mrppak.spring_project01.springboot.domain.posts;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class PostsRepositoryTest {

    @Autowired PostsRepository postsRepository;

    @AfterEach
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {

        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // when
        postsRepository.save(
                Posts.builder()
                        .title(title)
                        .content(content)
                        .author("mrppak@gmail.com")
                        .build()
        );

        // then
        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(0);
        Assertions.assertThat(posts.getTitle()).isEqualTo(title);
        Assertions.assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {

        // given
        LocalDateTime standardTime = LocalDateTime.of(2022, 01, 01, 0, 0, 0);
        Posts savePosts = postsRepository.save(
                Posts.builder()
                        .title("제목")
                        .content("내용")
                        .author("글쓴이")
                        .build()
        );

        // when
        Posts posts = postsRepository.findById(savePosts.getId()).get();
        System.out.println(">>>>>>>>>>>>>>> 테스트 : 등록시간: " + posts.getCreatedDate() + ", 수정시간: " + posts.getModifiedDate());

        // then
        Assertions.assertThat(posts.getCreatedDate()).isAfter(standardTime);
        Assertions.assertThat(posts.getModifiedDate()).isAfter(standardTime);
    }

}
