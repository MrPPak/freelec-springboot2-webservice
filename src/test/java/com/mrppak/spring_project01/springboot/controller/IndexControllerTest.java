package com.mrppak.spring_project01.springboot.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void 메인페이지_로딩() {

        String body = restTemplate.getForObject("/", String.class);

        System.out.println("body = " + body);
        Assertions.assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");
    }

}
