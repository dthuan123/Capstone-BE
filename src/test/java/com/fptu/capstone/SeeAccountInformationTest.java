package com.fptu.capstone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SeeAccountInformationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TestSeeOwnAccount() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/reader/account/seeInfo")
                .header("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("reader")))
                .andExpect(jsonPath("$.role.id", is(1)))
                .andReturn();
    }

    @Test
    public void TestSeeOtherAccount() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/reader/account/seeInfo")
                .header("userId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("creator")))
                .andExpect(jsonPath("$.role.id", is(2)))
                .andReturn();
    }

    @Test
    public void TestSeeOtherAccountWithNoId() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/reader/account/seeInfo")
                .header("userId", ""))
                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.name", is("creator")))
//                .andExpect(jsonPath("$.role.id", is(2)))
                .andReturn();
    }
}
