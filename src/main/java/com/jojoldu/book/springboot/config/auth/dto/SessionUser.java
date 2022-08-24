package com.jojoldu.book.springboot.config.auth.dto;

import com.jojoldu.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable { // 세션에 사용자 정보를 저장하기 위한 Dto 클래스
    // 직렬화 기능을 가진 세션 Dto
    private Long id;
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
