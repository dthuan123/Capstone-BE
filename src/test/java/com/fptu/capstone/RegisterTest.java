package com.fptu.capstone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fptu.capstone.entity.Role;
import com.fptu.capstone.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegisterSuccess() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        Role role = new Role();
        role.setId(1);
        user.setRole(role);
        user.setApproved(false);
        user.setName("vanhdao999");
        user.setPassword("vanhdao999");
        user.setEmail("vanhdao99@gmail.com");
        user.setPhone("0377999333");
        user.setEnabled(true);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testRegisterDuplicateName() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        Role role = new Role();
        role.setId(1);
        user.setRole(role);
        user.setApproved(false);
        user.setName("reader");
        user.setPassword("vanhdao99");
        user.setEmail("vanhdao99@gmail.com");
        user.setPhone("0377999333");
        user.setEnabled(true);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
