package com.fptu.capstone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fptu.capstone.entity.Alias;
import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.Comment;
import com.fptu.capstone.entity.User;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReaderComment {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void testReaderComment() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setId(1);
        User creator = new User();
        creator.setId(2);
        Book book = new Book();
        book.setId(1);
        book.setCreator(creator);
        Comment comment = new Comment();
        comment.setBook(book);
        comment.setUser(user);
        comment.setContent("My comment");

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/reader/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", is("My comment")))
                .andReturn();
    }

    @Test
    @Transactional
    public void testReaderCommentNoParams() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setId(1);
        User creator = new User();
        creator.setId(2);
        Book book = new Book();
        book.setId(1);
        book.setCreator(creator);
        Comment comment = new Comment();
        comment.setBook(book);
        comment.setUser(user);
        comment.setContent("My comment");

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/reader/comment")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Transactional
    public void testReaderCommentNoBook() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setId(1);
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setContent("My comment");

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/reader/comment")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Transactional
    public void testReaderCommentNoUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Comment comment = new Comment();
        comment.setContent("My comment");

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/reader/comment")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Transactional
    public void testReaderCommentWithoutParam() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/reader/comment")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
