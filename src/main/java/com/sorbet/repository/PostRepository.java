package com.sorbet.repository;

import com.sorbet.entity.Comment;
import com.sorbet.entity.Post;
import com.sorbet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCategory(String category);     // 게시글 카테고리 검색
    List<Post> findByTitle(String title);           // 제목으로 검색
    List<Post> findByWriter(String writer);         // 작성자 닉네임으로 검색
    List<Post> findAllByOrderByCreatedAtDesc();;
    long countByUser(User user);


}
