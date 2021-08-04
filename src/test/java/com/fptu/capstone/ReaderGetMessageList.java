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
public class ReaderGetMessageList {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetMessagesByUserIdExist() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/message-list")
                .header("page", 0)
                .header("pageSize", 5)
                .header("userId", 1)
                .header("searchKeyword", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",  hasSize(2)))
                .andReturn();
    }

    @Test
    public void testGetMessagesByUserIdNotExist() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/message-list")
                .header("page", 0)
                .header("pageSize", 5)
                .header("userId", 1000)
                .header("searchKeyword", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",  hasSize(0)))
                .andReturn();
    }

    @Test
    public void testGetMessagesByUserIdIsZero() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/message-list")
                .header("page", 0)
                .header("pageSize", 5)
                .header("userId", 0)
                .header("searchKeyword", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",  hasSize(0)))
                .andReturn();
    }

    @Test
    public void testGetMessagesByUserIdIsNegative() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/message-list")
                .header("page", 0)
                .header("pageSize", 5)
                .header("userId", -1)
                .header("searchKeyword", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",  hasSize(0)))
                .andReturn();
    }

    @Test
    public void testGetMessagesByKeyword() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/message-list")
                .header("page", 0)
                .header("pageSize", 5)
                .header("userId", 1)
                .header("searchKeyword", "bị đạo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].reportContent", is("Tôi thấy truyện bị đạo")))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void testGetMessagesWithNoParameters() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/message-list"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testGetMessagesWithNotExistPage() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/message-list")
                .header("page", 50)
                .header("pageSize", 5)
                .header("userId", 1)
                .header("searchKeyword", "bị đạo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andReturn();
    }
}
