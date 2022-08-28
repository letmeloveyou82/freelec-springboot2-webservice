package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private Long userId;

    private int viewCount;

    private int scrapCount;

    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.userId = entity.getUser().getId();
        this.viewCount = entity.getViewCount();
        this.scrapCount = entity.getScrapCount();
    }
}
