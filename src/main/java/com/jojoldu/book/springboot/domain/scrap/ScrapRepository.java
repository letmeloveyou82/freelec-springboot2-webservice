package com.jojoldu.book.springboot.domain.scrap;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    // 해당 유저가 스크랩을 한 적이 있는지 체크하는 용도로 사용
    Optional<Scrap> findByUserAndPosts(User user, Posts posts);

    // 게시글의 스크랩 수 카운트
    Optional<Integer> countByPosts(Posts posts);
}
