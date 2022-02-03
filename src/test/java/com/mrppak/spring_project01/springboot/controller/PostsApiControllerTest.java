package com.mrppak.spring_project01.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrppak.spring_project01.springboot.domain.posts.Posts;
import com.mrppak.spring_project01.springboot.domain.posts.PostsRepository;
import com.mrppak.spring_project01.springboot.web.dto.PostsSaveRequestDto;
import com.mrppak.spring_project01.springboot.web.dto.PostsUpdateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class PostsApiControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    WebApplicationContext context;

    MockMvc mvc;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @AfterEach
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER")
    public void saveTest() throws Exception {

        // given
        String body = mapper.writeValueAsString(
                PostsSaveRequestDto.builder()
                        .title("Test title")
                        .content("Test content")
                        .author("Test author")
                        .build()
        );

        // when
        ResultActions resultAction = mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/posts")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultAction
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }

    @Test
    @WithMockUser(roles="USER")
    public void Posts_수정() throws Exception {

        // given
        Posts savePosts = postsRepository.save(
                Posts.builder()
                        .title("test_title")
                        .content("test_content")
                        .author("test_author")
                        .build()
        );

        String body = mapper.writeValueAsString(
                PostsUpdateRequestDto.builder()
                        .title("update_title")
                        .content("update_content")
                        .build()
        );

        // when
        ResultActions resultAction = mvc.perform(
                MockMvcRequestBuilders.put("/api/v1/posts/" + savePosts.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        Posts posts = postsRepository.findById(savePosts.getId()).get();
        Assertions.assertThat(posts.getTitle()).isEqualTo("update_title");
        Assertions.assertThat(posts.getContent()).isEqualTo("update_content");
    }
}
