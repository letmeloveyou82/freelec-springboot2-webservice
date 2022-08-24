package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private User user;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author, User user){
        this.title = title;
        this.content = content;
        this.author = author;
        this.user = user;
    }

    // Post Entity로 변환
    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .user(user)
                .build();
    }
}
