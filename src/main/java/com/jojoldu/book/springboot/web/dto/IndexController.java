package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.config.auth.CustomOAuth2UserService;
import com.jojoldu.book.springboot.config.auth.LoginUser;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
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
    public String postsReadOrUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user){
        PostsResponseDto dto = postsService.findById(id);
        if(user != null){
            if(dto.getUserId().equals(user.getId())){
                model.addAttribute("posts", dto);
                return "posts-update";
            }
            else{
                postsService.updateViewCount(id);
                model.addAttribute("posts", dto);
                return "posts-read";
            }
        }
        return null;
    }

    @GetMapping("/users/update/{id}")
    public String userUpdate(@PathVariable Long id, Model model) {
        SessionUser dto = customOAuth2UserService.findById(id);
        model.addAttribute("user", dto);

        return "user-update";
    }
}
