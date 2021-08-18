package com.fptu.capstone;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class GetBookListByCreator {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBooks() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/books")
                .header("creatorId", 2)
                .header("searchKeyword", "")
                .header("page", 0)
                .header("pageSize", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andReturn();
    }

    @Test
    public void testGetBooksByKeyword() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/books")
                .header("creatorId", 2)
                .header("searchKeyword", "dã quái")
                .header("page", 0)
                .header("pageSize", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Từ dã quái bắt đầu tiến hóa thăng cấp")))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void testGetBooksByCreatorId() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/books")
                .header("creatorId", 1)
                .header("searchKeyword", "dã quái")
                .header("page", 0)
                .header("pageSize", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andReturn();
    }

    @Test
    public void testGetBooksWithNoParameters() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/books"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testGetBooksWithNotExistPage() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/books")
                .header("creatorId", 2)
                .header("searchKeyword", "dã quái")
                .header("page", 50)
                .header("pageSize", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andReturn();
    }
}
