package com.insurance.mvp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.mvp.controllers.Collateral;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(Collateral.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser
    @Test
    void authenticate_withValidToken_shouldReturn200() throws Exception {
        var request = new TokenDto("valid-token");

        mockMvc.perform(post("/collaterals/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty());
    }

    @WithMockUser
    @Test
    void authenticate_withBlankToken_shouldReturn400() throws Exception {
        var request = new TokenDto("");

        mockMvc.perform(post("/collaterals/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.token").value("توکن احراز الزامی است"));
    }

    private record TokenDto(String token) {}
}
