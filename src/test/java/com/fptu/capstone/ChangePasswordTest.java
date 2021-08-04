package com.fptu.capstone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fptu.capstone.entity.User;
import com.fptu.capstone.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChangePasswordTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testChangePasswordSuccess() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        MockMultipartFile oldPassword = new MockMultipartFile("oldPassword", "", "application/json", objectMapper.writeValueAsString("vanhdao").getBytes());
        MockMultipartFile newPassword = new MockMultipartFile("password", "", "application/json", objectMapper.writeValueAsString("vanhdao99").getBytes());
        MockMultipartFile userId = new MockMultipartFile("userId", "", "application/json", objectMapper.writeValueAsString(9).getBytes());

        MvcResult result = this.mockMvc.perform(multipart("/changePassword")
                        .file(oldPassword)
                        .file(newPassword)
                        .file(userId))
                        .andExpect(status().isOk())
                        .andReturn();
    }

    @Test
    public void testChangePasswordIncorrectOldPassword() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        MockMultipartFile oldPassword = new MockMultipartFile("oldPassword", "", "application/json", objectMapper.writeValueAsString("vanhvanh").getBytes());
        MockMultipartFile newPassword = new MockMultipartFile("password", "", "application/json", objectMapper.writeValueAsString("vanhvanh99").getBytes());
        MockMultipartFile userId = new MockMultipartFile("userId", "", "application/json", objectMapper.writeValueAsString(9).getBytes());

        MvcResult result = this.mockMvc.perform(multipart("/changePassword")
                .file(oldPassword)
                .file(newPassword)
                .file(userId))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content + "adad");
    }

    @Test
    public void testChangePasswordWithBlankOldPassword() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        MockMultipartFile oldPassword = new MockMultipartFile("oldPassword", "", "application/json", objectMapper.writeValueAsString("").getBytes());
        MockMultipartFile newPassword = new MockMultipartFile("password", "", "application/json", objectMapper.writeValueAsString("vanhvanh99").getBytes());
        MockMultipartFile userId = new MockMultipartFile("userId", "", "application/json", objectMapper.writeValueAsString(9).getBytes());

        MvcResult result = this.mockMvc.perform(multipart("/changePassword")
                .file(oldPassword)
                .file(newPassword)
                .file(userId))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
