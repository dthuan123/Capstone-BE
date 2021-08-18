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
public class CreatorGetChapters {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetChapters() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/chapters")
                .header("bookId", 2)
                .header("searchKeyword", "")
                .header("page", 0)
                .header("pageSize", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andReturn();
    }

    @Test
    public void testGetChaptersByKeyword() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/chapters")
                .header("bookId", 2)
                .header("searchKeyword", "tranh nhau")
                .header("page", 0)
                .header("pageSize", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name", is("Thanh khi tranh nhau nhan chu")))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void testGetChaptersByCreatorId() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/chapters")
                .header("bookId", 0)
                .header("searchKeyword", "")
                .header("page", 0)
                .header("pageSize", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andReturn();
    }

    @Test
    public void testGetChaptersWithNoParameters() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/chapters"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testGetChaptersWithNotExistPage() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/chapters")
                .header("bookId", 2)
                .header("searchKeyword", "dã quái")
                .header("page", 50)
                .header("pageSize", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andReturn();
    }
}
