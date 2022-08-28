package com.jojoldu.book.springboot.service.scrap;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.domain.scrap.Scrap;
import com.jojoldu.book.springboot.domain.scrap.ScrapRepository;
import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    // 스크랩 등록
    @Transactional
    public boolean addScrap(Long userId, Long postId){
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ postId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+ userId));
        // 중복 스크랩 방지
        if(!isNotAlreadyScrap(user, posts)){
            scrapRepository.save(new Scrap(posts, user));
            postsRepository.plusScrapCount(postId);
            return true;
        }
        return false;
    }

    // 스크랩 취소
    @Transactional
    public void cancelScrap(Long userId, Long postId){
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ postId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+ userId));
        Scrap scrap = scrapRepository.findByUserAndPosts(user, posts)
                .orElseThrow(() -> new IllegalArgumentException("해당 스크랩이 없습니다. id="));
        scrapRepository.delete(scrap);
        postsRepository.minusScrapCount(postId);
    }

    // 사용자가 이미 스크랩한 게시글인지 체크
    @Transactional
    private boolean isNotAlreadyScrap(User user, Posts posts) {
        return scrapRepository.findByUserAndPosts(user, posts).isPresent();
    }

    // 해당 게시물을 스크랩한 사용자 리스트 반환
    public List<String> count(Long postId, Long userId){
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ postId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+ userId));
        Integer postsScrapCount = scrapRepository.countByPosts(posts).orElse(0);
        List<String> resultData = new ArrayList<>(Arrays.asList(String.valueOf(postsScrapCount)));

        if(Objects.nonNull(user)){
            resultData.add(String.valueOf(isNotAlreadyScrap(user, posts)));
            return resultData;
        }
        return resultData;
    }
}
