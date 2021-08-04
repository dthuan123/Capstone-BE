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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReaderSearchMessages {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSearchMessagesWithoutKeyword() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/message-list")
                .header("page", 0)
                .header("pageSize", 5)
                .header("userId", 1)
                .header("searchKeyword", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",  hasSize(2)))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void testSearchMessagesByKeywordHaveNoResult() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/message-list")
                .header("page", 0)
                .header("pageSize", 5)
                .header("userId", 1)
                .header("searchKeyword", "báo cáo cho vui thôi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",  hasSize(0)))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void testSearchMessagesByKeywordHaveOneResult() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/message-list")
                .header("page", 0)
                .header("pageSize", 5)
                .header("userId", 1)
                .header("searchKeyword", "bị đạo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",  hasSize(1)))
                .andExpect(jsonPath("$.content[0].reportContent", is("Tôi thấy truyện bị đạo")))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void testSearchMessagesByKeywordHaveManyResult() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/message-list")
                .header("page", 0)
                .header("pageSize", 5)
                .header("userId", 1)
                .header("searchKeyword", "truyện"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",  hasSize(2)))
                .andExpect(jsonPath("$.content[0].reportContent", is("Tôi thấy truyện bị đạo")))
                .andExpect(jsonPath("$.content[1].reportContent", is("Truyện hay nhưng hơi xàm")))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    @Transactional
    public void testSearchMessagesWithoutParam() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/reader/message-list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}
