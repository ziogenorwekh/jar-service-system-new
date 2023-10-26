package com.jar.service.system.container.service.dockeraccess.mapper;

import com.spotify.docker.client.shaded.com.fasterxml.jackson.databind.JsonNode;
import com.spotify.docker.client.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ContainerResponseMessageParser {

    public String parser(String responseBody) {
        try {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("message").asText();
        } catch (Exception e) {
            return "Message parsing error.";
        }
    }
}
