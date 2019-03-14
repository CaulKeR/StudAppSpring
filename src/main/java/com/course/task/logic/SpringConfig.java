package com.course.task.logic;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan({"com.course.task.web", "com.course.task.logic"})
public class SpringConfig {

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    MarkDAO markDao() {
        return new MarkDAO();
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    SubjectDAO subjectDao() {
        return new SubjectDAO();
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    StudentDAO studentDao() {
        return new StudentDAO();
    }

    @Bean
    Utility utility() {
        return new Utility();
    }

}


