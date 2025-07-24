package com.sorbet.service;

import com.sorbet.entity.Post;
import com.sorbet.entity.User;
import com.sorbet.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 게시글 저장
    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    public void deletePostByIdAndUser(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new SecurityException("본인의 게시글만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }


    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    // 카테고리별 게시글 조회
    @Transactional(readOnly = true)
    public List<Post> findByCategory(String category) {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

        public Post findById(Long id) {
            return postRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        }

    public long getPostCountByUser(User user) {
        return postRepository.countByUser(user);
    }


    // 다른 서비스 메서드들...
    }



