package com.mrppak.spring_project01.springboot.service.posts;

import com.mrppak.spring_project01.springboot.domain.posts.Posts;
import com.mrppak.spring_project01.springboot.domain.posts.PostsRepository;
import com.mrppak.spring_project01.springboot.web.dto.PostsListResponseDto;
import com.mrppak.spring_project01.springboot.web.dto.PostsResponseDto;
import com.mrppak.spring_project01.springboot.web.dto.PostsSaveRequestDto;
import com.mrppak.spring_project01.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    public PostsResponseDto findById(Long id) {
        Posts postEntity = postsRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("해당 게시물이 없습니다. id = " + id)
        );
        return new PostsResponseDto(postEntity);
    }

    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id = " + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        postsRepository.delete(posts);
    }

}
