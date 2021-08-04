package com.fptu.capstone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ListBookByCategoryTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TestListBookByCategory() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/book-list")
                .header("categoryId", 1)
                .header("page", 0)
                .header("pageSize", 10))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id", is(5)))
                .andReturn();
    }

    @Test
    public void TestListBookWithIncorectCategory() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/book-list")
                .header("categoryId", 0)
                .header("page", 0)
                .header("pageSize", 10))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(0)))
                .andReturn();
    }
}
