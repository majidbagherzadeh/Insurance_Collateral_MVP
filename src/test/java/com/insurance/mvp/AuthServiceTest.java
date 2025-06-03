package com.insurance.mvp;

import com.insurance.mvp.dto.AuthResponse;
import com.insurance.mvp.dto.TokenRequest;
import com.insurance.mvp.service.CollateralServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthServiceTest {
    @InjectMocks
    private CollateralServiceImpl service;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void authenticate_shouldReturnToken() {
        TokenRequest request = new TokenRequest();
        request.setToken("sample");
        AuthResponse response = service.authenticate(request);
        assertThat(response.getAccessToken()).isNotBlank();
    }
}
