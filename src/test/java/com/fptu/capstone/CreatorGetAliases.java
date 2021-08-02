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
public class CreatorGetAliases {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAliases() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/aliases")
                .header("creatorId", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)))
                .andReturn();
    }

    @Test
    public void testGetAliasesByNotExistCreator() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/aliases")
                .header("creatorId", 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
                .andReturn();
    }

    @Test
    public void testGetAliasesByWithNoParams() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/creator/get/aliases"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
