package com.jojoldu.book.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String title;
    private String content;

    private int scrapCount;

    @Builder
    public PostsUpdateRequestDto(String title, String content, int scrapCount){
        this.title = title;
        this.content = content;
        this.scrapCount = scrapCount;
    }
}
