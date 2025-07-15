package com.sorbet.consulting.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; //게시글 제목
    
    @Column(length = 2000)
    private String content; //게시글 본문
    private String category; //카테고리
    private String writer; //작성자
    private String consultant; //상담사 ID
    private boolean rewarded; //상담완료 후 경험치 지급 여부
    
    @ElementCollection
    private List<String> tags; //태그
    private Date createdAt; //작성시간
    private boolean isPrivate; //비밀글 이므로 true 고정
}
