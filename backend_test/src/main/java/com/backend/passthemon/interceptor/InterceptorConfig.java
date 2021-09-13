package com.backend.passthemon.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
//    @Bean
//    public JwtInterceptor jwtInterceptor() {
//        return new JwtInterceptor();
//    }
//
//    @Bean
//    public SecurityInterceptor securityInterceptor(){return new SecurityInterceptor();}
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/user/login")
//                .excludePathPatterns("/user/signup")
//                .excludePathPatterns("/user/reset")
//                .excludePathPatterns("/user/sendEmail")
//                .excludePathPatterns("/error")
//                .excludePathPatterns("/test");
//        registry.addInterceptor(securityInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/user/login")
//                .excludePathPatterns("/user/signup")
//                .excludePathPatterns("/user/reset")
//                .excludePathPatterns("/user/sendEmail")
//                .excludePathPatterns("/error")
//                .excludePathPatterns("/test");
//    }
//
//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOriginPattern("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setAllowCredentials(true);
//        return corsConfiguration;
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig());
//        return new CorsFilter(source);
//    }
}
