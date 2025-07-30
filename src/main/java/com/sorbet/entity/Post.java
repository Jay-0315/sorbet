// src/main/java/com/sorbet/entity/Post.java
package com.sorbet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private String category;
    private String writer;
    private String tags;

    @Column(nullable = false)  // 제목 필수
    private String title;

    @Column(nullable = false)
    private Long views = 0L;

    @Lob
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 작성자 ID (FK)
    private User user;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

