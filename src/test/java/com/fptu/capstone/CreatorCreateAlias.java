package com.fptu.capstone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fptu.capstone.entity.Alias;
import com.fptu.capstone.entity.Book;
import com.fptu.capstone.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CreatorCreateAlias {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void testCreateDuplicateAlias() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setId(2);
        Alias alias = new Alias();
        alias.setName("nghệ danh");
        alias.setUser(user);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/creator/create/alias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alias)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Transactional
    public void testCreateAlias() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setId(2);
        Alias alias = new Alias();
        alias.setName("testingAlias");
        alias.setUser(user);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/creator/create/alias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alias)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Transactional
    public void testCreateAliasWithoutParam() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/creator/create/alias")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
