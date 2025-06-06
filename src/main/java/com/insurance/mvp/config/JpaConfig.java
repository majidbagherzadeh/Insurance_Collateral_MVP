package com.insurance.mvp.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.insurance.mvp.repository")
@EntityScan(basePackages = "com.insurance.mvp.entity")
public class JpaConfig {
}
