package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.LoginUser;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsListResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts") // 게시글 등록
    public ResponseEntity<Long> save(@RequestBody PostsSaveRequestDto requestDto, @LoginUser SessionUser user){
       return ResponseEntity.ok(postsService.save(requestDto, user.getEmail()));
    }

    @PutMapping("/api/v1/posts/{id}") // 게시글 수정
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}") // 게시글 조회
    public PostsResponseDto findById (@PathVariable Long id){
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}") // 게시글 삭제
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/posts/all") // 게시글 전체 조회
    public List<PostsListResponseDto> getPostsAll () {
        return postsService.findAllDesc();
    }

    @GetMapping("/api/v1/posts/paging") // 게시글 5개씩 조회(더보기 기능)
    public Slice<PostsListResponseDto> getPostsWithPaging(@PageableDefault(size = 5, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        return postsService.pageList(pageable);
    }
}
