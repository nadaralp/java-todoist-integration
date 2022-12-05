package com.example.springfirstdemo.todoist;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TodoistClientImpl implements TodoistClient {
    private static final Logger log = LoggerFactory.getLogger(TodoistClientImpl.class);
    private static final String TODOIST_API_URL = "https://api.todoist.com/rest";

    @Override
    public TodoistCreateTaskResponse addTodoistTask(TodoistCreateTaskRequest request, String bearerAuthToken) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // To find the module to register Java8 API for date and time
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        URI addTaskUri = URI.create(TODOIST_API_URL + "/v2/tasks");
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest
                    .newBuilder(addTaskUri)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + bearerAuthToken)
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(request)
                    ))
                    .build();


            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            log.info("received http response from todoist: {}", httpResponse);

            TodoistCreateTaskResponse todoistCreateTaskResponse = objectMapper.readValue(httpResponse.body(), TodoistCreateTaskResponse.class);
            log.info("tasked added to todoist. response: {}", todoistCreateTaskResponse);
            return todoistCreateTaskResponse;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
