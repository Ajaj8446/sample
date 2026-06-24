package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloReturnsOk() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void greetReturnsOkWithValidName() throws Exception {
        mockMvc.perform(get("/greet").param("name", "World"))
                .andExpect(status().isOk());
    }

    @Test
    void greetReturnsBadRequestWhenNameMissing() throws Exception {
        mockMvc.perform(get("/greet"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    void greetReturnsBadRequestWhenNameBlank() throws Exception {
        mockMvc.perform(get("/greet").param("name", "  "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Parameter 'name' must not be blank"));
    }

    @Test
    void greetByIdReturnsOkWithValidId() throws Exception {
        mockMvc.perform(get("/greet/1"))
                .andExpect(status().isOk());
    }

    @Test
    void greetByIdReturnsBadRequestForNegativeId() throws Exception {
        mockMvc.perform(get("/greet/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("ID must be a positive number"));
    }

    @Test
    void greetByIdReturnsBadRequestForNonNumericId() throws Exception {
        mockMvc.perform(get("/greet/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void methodNotAllowedReturnsStructuredError() throws Exception {
        mockMvc.perform(post("/"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.status").value(405))
                .andExpect(jsonPath("$.error").value("Method Not Allowed"));
    }

    @Test
    void notFoundReturnsStructuredError() throws Exception {
        mockMvc.perform(get("/nonexistent"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}
