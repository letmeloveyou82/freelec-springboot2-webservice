package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.scrap.Scrap;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScrapDto {
    private Long id;
    private Long postId;
    private Long userId;

    public ScrapDto(Scrap entity){
        this.id = entity.getId();
        this.postId = entity.getPosts().getId();
        this.userId = entity.getUser().getId();
    }
}
