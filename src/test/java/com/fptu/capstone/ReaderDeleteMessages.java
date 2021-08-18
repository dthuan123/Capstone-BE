package com.fptu.capstone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

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
    public void testDeleteMessageByReportId() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.delete("/reader/delete-message/3")
                .header("reportId", 3))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Transactional
    public void testDeleteMessageWithoutParam() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.delete("/reader/delete-message")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
