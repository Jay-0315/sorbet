package com.sorbet.consulting.service;

import com.sorbet.consulting.domain.Post;
import com.sorbet.consulting.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    //게시글 저장
    //작성 시간 자동 설정 + 무조건 비밀글로 처리
    public Post save(Post post) {
        post.setCreatedAt(new Date());
        post.setPrivate(true);
        return postRepository.save(post);
    }
    //전체 게시글 조회
    public List<Post> getAll() {
        return postRepository.findAll();
    }
    //id로 특정 게시글 조회
    public Optional<Post> getPost(Long id) {
        return postRepository.findById(id);
    }
    //특정 카테고리로 필터링
    public List<Post> getByCategory(String category) {
        return postRepository.findPostByCategory(category);
    }
    //특정 상담사가 열람 가능한 글 조회
    public List<Post> getByConsultant(String consultantId) {
        return postRepository.findByConsultant(consultantId);
    }
}