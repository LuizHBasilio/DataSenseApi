package com.DataSense.DataSenseApi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class EquipamentDataControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReceiveCsvFile() throws Exception {
        String csvContent = "equipmentId,timestamp,value\n" +
                            "EQ-12495,2023-02-15T01:30:00.000-05:00,78.42\n" +
                            "EQ-12496,2023-02-16T01:30:00.000-05:00,80.50";

        mockMvc.perform(multipart("/datasense/api/v1/equipments/upload")
                .file("file", csvContent.getBytes())
                .with(httpBasic("admin", "admin")))
                .andExpect(status().isOk())
                .andExpect(content().string("CSV file was been processed"));
    }
}
