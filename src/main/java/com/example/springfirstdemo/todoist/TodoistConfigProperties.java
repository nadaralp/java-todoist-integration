package com.example.springfirstdemo.todoist;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("todoistProperties") // Sets the name of the bean
@ConfigurationProperties(prefix = "custom.todoist")
@Getter
@Setter
@ToString
public class TodoistConfigProperties {
    private String todoistDevApiKey;
}
