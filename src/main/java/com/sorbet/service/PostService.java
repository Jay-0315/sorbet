package com.sorbet.service;

import com.sorbet.dto.PostDto;
import com.sorbet.entity.Post;
import com.sorbet.entity.User;
import com.sorbet.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostReactionService postReactionService;

    // 게시글 저장
    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    // 게시글 삭제 (작성자 본인만)
    public void deletePostByIdAndUser(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        if (!post.getUser().getId().equals(user.getId())) {
            throw new SecurityException("본인의 게시글만 삭제할 수 있습니다.");
        }
        postRepository.delete(post);
    }

    // 조회수 증가
    @Transactional
    public Post increaseViewCount(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));
        post.setViews(post.getViews() + 1);
        return post;
    }

    private String extractFirstImageUrl(String html) {
        if (html == null) return null;
        Pattern pattern = Pattern.compile("<img[^>]+src=[\"']([^\"']+)[\"']");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1); // 첫 번째 이미지 URL 반환
        }
        return null;
    }

    // 전체 게시글 조회
    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    // 카테고리별 게시글 조회
    @Transactional(readOnly = true)
    public List<Post> findByCategory(String category) {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    // 게시글 삭제 (ID 기준)
    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    // 게시글 ID로 조회
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
    }

    // 유저별 게시글 수
    public long getPostCountByUser(User user) {
        return postRepository.countByUser(user);
    }

    // 홈 화면용 게시글 DTO 리스트 (페이징 + 카테고리)
    public List<PostDto> getHomePostSummaries(Pageable pageable, String category) {
        Page<Post> posts;

        if (category == null || category.equals("all")) {
            posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        } else {
            posts = postRepository.findByCategoryOrderByCreatedAtDesc(category, pageable);
        }

        return posts.stream()
                .map(post -> new PostDto(
                        post.getId(),
                        post.getTitle(),
                        post.getWriter(),
                        post.getCategory(),
                        post.getCreatedAt(),
                        post.getViews(),
                        postReactionService.getLikeCount(post.getId()),
                        extractFirstImageUrl(post.getContent())
                )).toList();
    }

    // ✅ 검색 기능 - 제목에서 keyword가 포함된 게시글을 검색 (대소문자 구분 없이)
    @Transactional(readOnly = true)
    public Page<PostDto> searchByKeyword(String keyword, Pageable pageable) {
        Page<Post> postPage = postRepository.findByTitleContainingIgnoreCase(keyword, pageable);

        return postPage.map(post -> new PostDto(
                post.getId(),
                post.getTitle(),
                post.getWriter(),
                post.getCategory(),
                post.getCreatedAt(),
                post.getViews(),
                (postReactionService.getLikeCount(post.getId())),
                extractFirstImageUrl(post.getContent())

        ));
    }
}
