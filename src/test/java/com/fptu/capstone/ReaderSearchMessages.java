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
    public void testSearchMessagesBySpecialKeyword() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/message-list")
                .header("page", 0)
                .header("pageSize", 5)
                .header("userId", 1)
                .header("searchKeyword", "-"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",  hasSize(0)))
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
                .header("searchKeyword", "b??o c??o cho vui th??i"))
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
                .header("searchKeyword", "b??? ?????o"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",  hasSize(1)))
                .andExpect(jsonPath("$.content[0].reportContent", is("T??i th???y truy???n b??? ?????o")))
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
                .header("searchKeyword", "truy???n"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",  hasSize(2)))
                .andExpect(jsonPath("$.content[0].reportContent", is("T??i th???y truy???n b??? ?????o")))
                .andExpect(jsonPath("$.content[1].reportContent", is("Truy???n hay nh??ng h??i x??m")))
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
