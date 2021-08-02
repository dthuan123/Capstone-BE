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
        String password = "reader";
        user.setPassword(password);

        MvcResult result = this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("reader")))
                .andExpect(jsonPath("$.role.id", is(1)))
                .andReturn();
    }
    @Test
    public void testCreatorLogin() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setName("creator");
        String password = "creator";
        user.setPassword(password);

        MvcResult result = this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("creator")))
                .andExpect(jsonPath("$.role.id", is(2)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content + "adad");
    }

    @Test
    public void testAdminLogin() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setName("admin");
        String password = "admin";
        user.setPassword(password);

        MvcResult result = this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("admin")))
                .andExpect(jsonPath("$.role.id", is(3)))
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
                .andExpect(jsonPath("$.name", is(null)))
                .andExpect(status().isOk())
                .andReturn();
//            assertThat(result).isNotNull();
    }


}
