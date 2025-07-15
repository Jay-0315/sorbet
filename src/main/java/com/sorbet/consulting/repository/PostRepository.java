package com.sorbet.consulting.repository;

import com.sorbet.consulting.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByConsultant(String consultant);

    List<Post> findPostByCategory(String category);
}