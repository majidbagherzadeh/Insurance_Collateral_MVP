package com.insurance.mvp.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security.auth")
@EnableConfigurationProperties(TokenProperties.class)
public class TokenProperties {
    private List<String> tokens;
}
