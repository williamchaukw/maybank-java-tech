package com.kwchau.MaybankBackendTechTest;

import com.kwchau.MaybankBackendTechTest.Interceptor.AuthenticationInterceptor;
import com.kwchau.MaybankBackendTechTest.Interceptor.LoggingInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor());
        registry.addInterceptor(new AuthenticationInterceptor());
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}

