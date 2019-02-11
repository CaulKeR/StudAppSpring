package com.course.task.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    MarkDAO getMarkDao() {
        return new MarkDAO();
    }

    @Bean
    SubjectDAO getSubjectDao() {
        return new SubjectDAO();
    }

    @Bean
    StudentDAO getStudentDao() {
        return new StudentDAO();
    }

    @Bean
    Utility getUtility() {
        return new Utility();
    }

}