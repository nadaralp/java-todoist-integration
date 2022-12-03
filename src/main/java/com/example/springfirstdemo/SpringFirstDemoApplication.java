package com.example.springfirstdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


class TestClass {


}

@SpringBootApplication
//@SpringBootApplication is a convenience annotation that adds all of the following:
// @Configuration: Tags the class as a source of bean definitions for the application context.
// @EnableAutoConfiguration: tells Spring Boot to start adding beans based on classpath settings
// @ComponentScan: tells Spring to look for other components, configurations, and services in the package
public class SpringFirstDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringFirstDemoApplication.class, args);
    }

    @Bean
    public TestClass testBean() {
        System.out.println("This is a test class method running");
        return new TestClass();
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//            System.out.println("Inspecting the beans added to the application context");
//
//            for (String beanName : ctx.getBeanDefinitionNames()) {
//                System.out.println("bean name: " + beanName);
//            }
//        };
//    }
}
