package com.fptu.capstone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReaderLikeList {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetLikeList() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/get/likes")
                .header("userId", 1)
                .header("page", 0)
                .header("pageSize", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(4)))
                .andReturn();
    }

    @Test
    public void testGetLikeListByNotExistId() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/get/likes")
                .header("userId", 0)
                .header("page", 0)
                .header("pageSize", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andReturn();
    }

    @Test
    public void testGetLikeListWithNoParameters() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/get/likes"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testGetLikeListWithNotExistPage() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/get/likes")
                .header("userId", 2)
                .header("page", 50)
                .header("pageSize", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andReturn();
    }
}
