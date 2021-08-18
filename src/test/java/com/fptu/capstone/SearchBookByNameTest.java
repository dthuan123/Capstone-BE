package com.fptu.capstone;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class SearchBookByNameTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TestSearchBookByName() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/search-book")
                .header("searchword", "dã quái"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andReturn();
    }

    @Test
    public void TestSearchBookWithBlank() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/search-book")
                .header("searchword", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andReturn();
    }

    @Test
    public void TestSearchBookWithSpecialCharacter() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/search-book")
                .header("searchword", "-"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(19)))
                .andReturn();
    }

    @Test
    public void TestSearchBookWithNoResult() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/search-book")
                .header("searchword", "hôm qua có gì"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
                .andReturn();
    }
}
