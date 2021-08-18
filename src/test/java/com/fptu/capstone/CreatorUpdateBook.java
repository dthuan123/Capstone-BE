package com.fptu.capstone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fptu.capstone.entity.Book;
import com.fptu.capstone.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CreatorUpdateBook {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    public void testUpdateBook() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = bookRepository.findById(1);
        MockMultipartFile coverImage = new MockMultipartFile("coverImage", "", "application/json", "{\"image\": \"src/main/content/images/books/1.png\"}".getBytes());
        MockMultipartFile bookFile = new MockMultipartFile("book", "", "application/json", objectMapper.writeValueAsString(book).getBytes());

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.multipart("/creator/update/book")
                .file(coverImage)
                .file(bookFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is((true))))
                .andReturn();
    }

    @Test
    @Transactional
    public void testUpdateBookWithoutBookParam() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MockMultipartFile coverImage = new MockMultipartFile("coverImage", "", "application/json", "{\"image\": \"src/main/content/images/books/1.png\"}".getBytes());

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.multipart("/creator/update/book")
                .file(coverImage))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Transactional
    public void testUpdateBookWithoutFileParam() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = bookRepository.findById(1);
        MockMultipartFile bookFile = new MockMultipartFile("book", "", "application/json", objectMapper.writeValueAsString(book).getBytes());

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.multipart("/creator/update/book")
                .file(bookFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is((true))))
                .andReturn();
    }
}
