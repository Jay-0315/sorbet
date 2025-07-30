package com.sorbet.repository;

import com.sorbet.entity.Post;
import com.sorbet.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 카테고리로 게시글 조회 (단순 리스트)
    List<Post> findByCategory(String category);

    // 제목으로 정확히 일치하는 게시글 조회
    List<Post> findByTitle(String title);

    // 작성자로 조회
    List<Post> findByWriter(String writer);

    // 생성일 기준 전체 게시글 내림차순 정렬 (리스트)
    List<Post> findAllByOrderByCreatedAtDesc();

    // 사용자별 게시글 수 조회
    long countByUser(User user);

    // 전체 게시글 페이징 + 최신순 정렬
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 카테고리별 게시글 페이징 + 최신순 정렬
    Page<Post> findByCategoryOrderByCreatedAtDesc(String category, Pageable pageable);

    // ✅ 제목 검색 (대소문자 구분 없이 포함되는 항목)
    Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
