package com.fptu.capstone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ListCommentInHomePageTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TestGet10CommentNewest() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/get-top-newest-comment-book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andReturn();
    }
}
