package com.sorbet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String writer;
    private String category;
    private LocalDateTime createdAt;
    private long views;
    private long likeCount;
    private String thumbnailUrl;


}
