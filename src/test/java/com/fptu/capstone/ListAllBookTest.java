package com.fptu.capstone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ListAllBookTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TestListAllBookSortByName() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/all-books")
                .header("page", 0)
                .header("pageSize", 5)
                .header("sort", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(103)))
                .andExpect(jsonPath("$.content[0].name", is("123")))
                .andReturn();
    }

    @Test
    public void TestListAllBookSortByUpdatedDate() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/all-books")
                .header("page", 0)
                .header("pageSize", 5)
                .header("sort", "updatedDate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(103)))
                .andReturn();
    }

    @Test
    public void TestListAllBookSortByStartedDate() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/all-books")
                .header("page", 0)
                .header("pageSize", 5)
                .header("sort", "date"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(10)))
                .andExpect(jsonPath("$.content[0].name", is("Thay chị lấy chồng")))
                .andReturn();
    }

    @Test
    public void TestListAllBookSortByLikes() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/all-books")
                .header("page", 0)
                .header("pageSize", 5)
                .header("sort", "likes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(10)))
                .andExpect(jsonPath("$.content[0].name", is("Thay chị lấy chồng")))
                .andReturn();
    }
    @Test
    public void TestListAllBookSortByNull() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/all-books")
                .header("page", 0)
                .header("pageSize", 5)
                .header("sort", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(103)))
                .andExpect(jsonPath("$.content[0].name", is("123")))
                .andReturn();
    }
}
