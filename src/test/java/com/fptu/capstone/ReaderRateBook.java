package com.fptu.capstone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fptu.capstone.entity.Book;
import com.fptu.capstone.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReaderRateBook {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    public void testRateBook() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = bookRepository.findById(1);
        MockMultipartFile rating = new MockMultipartFile("rating", "", "application/json", objectMapper.writeValueAsString(5).getBytes());
        MockMultipartFile bookFile = new MockMultipartFile("book", "", "application/json", objectMapper.writeValueAsString(book).getBytes());

        MvcResult result = this.mockMvc.perform(multipart("/reader/rate")
                .file(bookFile)
                .file(rating))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRating", is(10)))
                .andReturn();
    }

    @Test
    @Transactional
    public void testRateUnexistBook() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = bookRepository.findById(0);
        MockMultipartFile rating = new MockMultipartFile("rating", "", "application/json", objectMapper.writeValueAsString(5).getBytes());
        MockMultipartFile bookFile = new MockMultipartFile("book", "", "application/json", objectMapper.writeValueAsString(book).getBytes());

        MvcResult result = this.mockMvc.perform(multipart("/reader/rate")
                .file(bookFile)
                .file(rating))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testRateBookWithNoParams() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/reader/rate"))
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
    }
}
