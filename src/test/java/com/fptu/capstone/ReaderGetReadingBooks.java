package com.fptu.capstone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReaderGetReadingBooks {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetReadingBook() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/get/history")
                .header("userId", 1)
                .header("page", 0)
                .header("pageSize", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",  hasSize(3)))
                .andReturn();
    }

    @Test
    @Transactional
    public void testGetReadingBookWithoutParam() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/reader/create-report")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
