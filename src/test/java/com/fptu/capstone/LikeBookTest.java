package com.fptu.capstone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LikeBookTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TestLikeBook() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/updateLike")
                .header("likeCount", "46")
                .header("bookId", "16")
                .header("userId", "28"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.likes", is(47)))
                .andReturn();
    }

    @Test
    public void TestLikeBookWithAlreadyLikedBook() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/updateLike")
                .header("likeCount", "47")
                .header("bookId", "16")
                .header("userId", "28"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.likes", is(46)))
                .andReturn();
    }
}
