package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
    void helloReturnsGreeting() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("Hello from Spring Boot!"));
    }

    @Test
    void greetReturnsOkWithValidName() throws Exception {
        mockMvc.perform(get("/greet").param("name", "World"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("Hello, World!"));
    }

    @Test
    void greetReturnsBadRequestWhenNameMissing() throws Exception {
        mockMvc.perform(get("/greet"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void greetReturnsBadRequestWhenNameBlank() throws Exception {
        mockMvc.perform(get("/greet").param("name", "  "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("Parameter 'name' must not be blank"));
    }

    @Test
    void greetByIdReturnsOkWithValidId() throws Exception {
        mockMvc.perform(get("/greet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("Hello, user #1!"));
    }

    @Test
    void greetByIdReturnsBadRequestForNegativeId() throws Exception {
        mockMvc.perform(get("/greet/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("ID must be a positive number"));
    }

    @Test
    void greetByIdReturnsBadRequestForNonNumericId() throws Exception {
        mockMvc.perform(get("/greet/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @WithMockUser
    void methodNotAllowedReturnsStructuredError() throws Exception {
        mockMvc.perform(post("/").with(csrf()))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void notFoundReturnsStructuredError() throws Exception {
        mockMvc.perform(get("/greet/1/details"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("The requested resource was not found"));
    }

    @Test
    void unauthenticatedRequestToProtectedEndpointReturnsForbidden() throws Exception {
        mockMvc.perform(get("/admin/settings"))
                .andExpect(status().isForbidden());
    }
}
