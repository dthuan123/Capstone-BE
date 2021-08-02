package com.fptu.capstone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fptu.capstone.entity.User;
import com.fptu.capstone.service.MD5Library;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MD5Library md5Library;

    @Test
    public void testReaderLogin() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setName("reader");
        String password = md5Library.md5("reader");
        user.setPassword(password);

        MvcResult result = this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("reader")))
                .andExpect(jsonPath("$.password", is("56adcfcf54d78f1dbda34b57861bb48a")))
                .andExpect(jsonPath("$.user.role.id", is(1)))
                .andReturn();
//            assertThat(result).isNotNull();
    }
    @Test
    public void testCreatorLogin() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setName("creator");
        String password = md5Library.md5("creator");
        user.setPassword(password);

        MvcResult result = this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("creator")))
                .andExpect(jsonPath("$.password", is("2c3b05bd3272da82ccbd4f520d2b5de4")))
                .andExpect(jsonPath("$.user.role.id", is(2)))
                .andReturn();
//            assertThat(result).isNotNull();
    }

    @Test
    public void testAdminLogin() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setName("admin");
        String password = md5Library.md5("admin");
        user.setPassword(password);

        MvcResult result = this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("admin")))
                .andExpect(jsonPath("$.password", is("8cef5c02dafb8580bad2b4bfdc1b9f9e")))
                .andExpect(jsonPath("$.user.role.id", is(2)))
                .andReturn();
//            assertThat(result).isNotNull();
    }

    @Test
    public void testInCorrectLogin() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setName("vanhdao");
        String password = md5Library.md5("vanhdao999");
        user.setPassword(password);

        MvcResult result = this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("vanhdao")))
                .andExpect(jsonPath("$.password", is("ef144aff444dd46251b643370ec5e0dd")))
                .andReturn();
//            assertThat(result).isNotNull();
    }


}
