package com.jojoldu.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void return_hello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk()) // HTTP Header의 상태 검증
                .andExpect(content().string(hello)); // 응답 본문의 내용 검증
    }
    
    @Test
    public void return_helloDto() throws Exception {
        String name = "hello";
        int amount = 1000;
        
        mvc.perform(
                get("/hello/dto")
                    .param("name", name) 
                    .param("amount", String.valueOf(amount))) // param은 값으로 string만 허용
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // $를 기준으로 필드명을 명시
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
