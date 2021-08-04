package com.fptu.capstone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdminUserEnabled {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBooks() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/admin/user-enabled")
                .header("userid", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is((true))))
                .andReturn();
//        String content = result.getResponse().getContentAsString();
//        System.out.println(content);
    }

    @Test
    public void testGetBooksFail() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/admin/user-enabled"))
//                .header("userid", 2))
                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$[0].id", is(2)))
                .andReturn();
//        String content = result.getResponse().getContentAsString();
//        System.out.println(content);
    }

    @Test
    public void testGetBooks1() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/admin/user-enabled")
                .header("userid", 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is((true))))
                .andReturn();
//        String content = result.getResponse().getContentAsString();
//        System.out.println(content);
    }

    @Test
    public void testGetBooks2() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/admin/user-enabled")
                .header("userid", 4))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is((true))))
                .andReturn();
//        String content = result.getResponse().getContentAsString();
//        System.out.println(content);
    }

    @Test
    public void testGetBooks3() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/admin/user-enabled")
                .header("userid", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is((true))))
                .andReturn();
//        String content = result.getResponse().getContentAsString();
//        System.out.println(content);
    }
}
