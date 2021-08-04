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
public class CreatorGetBook {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBook() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/book")
                .header("bookId", 8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Người tìm xác")))
                .andReturn();
    }

    @Test
    public void testGetBookWithUnexistId() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/book")
                .header("bookId", 1000))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    @Test
    public void testGetBookWithNoParams() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/book"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
