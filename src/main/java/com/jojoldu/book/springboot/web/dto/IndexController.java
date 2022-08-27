package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.config.auth.CustomOAuth2UserService;
import com.jojoldu.book.springboot.config.auth.LoginUser;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
@Slf4j
public class IndexController {
    private final PostsService postsService;
    private final CustomOAuth2UserService customOAuth2UserService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());
        if(user!=null){
            model.addAttribute("user", customOAuth2UserService.findById(user.getId()));
            // model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/{id}") // 특정 게시물 조회 페이지
    public String postsRead(@PathVariable Long id, Model model, @LoginUser SessionUser user){
        PostsResponseDto dto = postsService.findById(id);
        if(user != null){
            if(!dto.getUserId().equals(user.getId())){
                postsService.updateViewCount(id);
            }
        }
        model.addAttribute("posts", dto);
        return "posts-read";
    }

    @GetMapping("/posts/update/{id}") // 특정 게시글 수정 페이지
    public String postsUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user){
        PostsResponseDto dto = postsService.findById(id);

        if(user != null){
            if(dto.getUserId().equals(user.getId())){
                log.trace("게시글 작성자 본인입니다.");
                // 프론트에 post 정보 넘겨주기
                model.addAttribute("posts", dto);
            }
            else{
                log.trace("게시글 작성자가 아닙니다. 수정 권한은 작성자에게만 있습니다.");
                // 알림창을 띄우거나 아니면 저 수정 버튼을 작성자 본인일 경우에만 보이도록 하기
            }
        }
        return "posts-update";
    }

    @GetMapping("/users/update/{id}")
    public String userUpdate(@PathVariable Long id, Model model) {
        SessionUser dto = customOAuth2UserService.findById(id);
        model.addAttribute("user", dto);

        return "user-update";
    }
}
