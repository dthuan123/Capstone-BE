package com.fptu.capstone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReaderDeleteMessages {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDeleteMessageByReportIdExist() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/delete-message")
                .header("reportId", 3))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteMessageByReportIdNotExist() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/delete-message")
                .header("reportId", 3))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteMessageByReportIdIsZero() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/delete-message")
                .header("reportId", 0))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteMessageByReportIdIsNegative() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/delete-message")
                .header("reportId", -1))
                .andExpect(status().isOk())
                .andReturn();
    }
}
