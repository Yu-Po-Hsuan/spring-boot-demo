package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.support.NullValue;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getById() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/students/{studentId}",3)
                .header("headerName", "headerValue")
                .queryParam("graduate", "true");

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", equalTo(3)))
                .andExpect(jsonPath("$.name", notNullValue()))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        System.out.println("response body's content is :" + body);
    }

    @Test
    public void create() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\":\"Mike\",\n" +
                        "  \"score\": 59.9,\n" +
                        "  \"graduate\": false\n" +
                        "}");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));
    }

    @Test
    public void update() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/students/{studentId}",6)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\":\"Hank\",\n" +
                        "  \"score\": 59.9,\n" +
                        "  \"graduate\": false\n" +
                        "}");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }

    @Test
    @Transactional
    public void delete() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/students/{studentId}",4);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }
}