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
public class ReaderHistory {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetHistory() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/get/history")
                .header("userId", 1)
                .header("page", 0)
                .header("pageSize", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andReturn();
    }

    @Test
    public void testGetHistoryByNotExistId() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/get/history")
                .header("userId", 0)
                .header("page", 0)
                .header("pageSize", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andReturn();
    }

    @Test
    public void testGetHistoryWithNoParameters() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/get/history"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
