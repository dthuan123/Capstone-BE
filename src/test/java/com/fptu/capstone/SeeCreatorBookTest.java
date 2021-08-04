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
public class SeeCreatorBookTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TestSeeBookOfCreatorId2() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/list-book-by-creator")
                .header("creatorId", 2)
                .header("page", 0)
                .header("pageSize", 10))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andReturn();
    }

    @Test
    public void TestSeeBookOfCreatorId4() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/list-book-by-creator")
                .header("creatorId", 4)
                .header("page", 0)
                .header("pageSize", 10))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(14)))
                .andReturn();
    }

    @Test
    public void TestSeeBookOfCreatorNoResult() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/list-book-by-creator")
                .header("creatorId", 3)
                .header("page", 0)
                .header("pageSize", 10))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andReturn();
    }
    @Test
    public void TestSeeBookOfCreatorNull() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/list-book-by-creator")
                .header("creatorId", "")
                .header("page", 0)
                .header("pageSize", 10))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
