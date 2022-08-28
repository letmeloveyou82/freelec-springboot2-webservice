package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import com.jojoldu.book.springboot.domain.scrap.Scrap;
import com.jojoldu.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    // user 테이블의 user_id 참조키(FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 테이블 칼럼 - 조회수
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int viewCount;

    // 게시글이 삭제되면 스크랩 기록도 삭제
    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    Set<Scrap> scrap = new HashSet<>();

    // 테이블 칼럼 - 스크랩수
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int scrapCount;

    @Builder
    public Posts(String title, String content, String author, User user, int viewCount, int scrapCount){
        this.title = title;
        this.content = content;
        this.author = author;
        this.user = user;
        this.viewCount = viewCount;
        this.scrapCount = scrapCount;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
