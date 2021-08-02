package com.fptu.capstone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CreatorGetChapter {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetChapter() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/chapter")
                .header("chapterId", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Tang len pham chat va thien phu")))
                .andReturn();
    }

    @Test
    public void testGetChapterWithUnexistId() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/chapter")
                .header("chapterId", 1000))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    @Test
    public void testGetChapterWithNoParams() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/chapter"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
