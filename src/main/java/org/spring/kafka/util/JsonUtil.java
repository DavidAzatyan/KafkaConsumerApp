package org.spring.kafka.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonUtil {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static <T> Optional<T> parse(String message, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return Optional.ofNullable(objectMapper.readValue(message, clazz));
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.INFO, "not person json");
        }
        return Optional.empty();
    }

    public static boolean isValid(String json){
        try {
            new JSONObject(json);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    public static Map<String, Object> convertJsonStringToMap(String jsonString){
        Map<String, Object> jsonMap = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonMap = objectMapper.readValue(jsonString, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonMap;
    }

    public static JsonNode convertJsonStringToJsonNode(String jsonString){
        JsonNode jsonNode = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonNode;
    }
}
