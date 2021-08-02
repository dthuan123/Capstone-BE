package com.fptu.capstone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fptu.capstone.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReaderCreateReport {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateReport() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User userSender = new User();
        userSender.setId(2);
        User userReceiver = new User();
        userReceiver.setId(3);
        Book book = new Book();
        book.setId(3);
        book.setCreator(userSender);
        ReportStatus reportStatus = new ReportStatus();
        reportStatus.setStatusId(1);
        Report report = new Report();
        report.setReportContent("test create new report");
        report.setReportedDate(new Date(2021-8-01));
        report.setResponseContent("test response content of report");
        report.setResponseDate(new Date(2021-8-03));
        report.setUserSender(userSender);
        report.setUserReceiver(userReceiver);
        report.setBook(book);
        report.setStatusId(reportStatus);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/reader/create-report")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(report)))
                .andExpect(status().isOk())
                .andReturn();
    }
}
