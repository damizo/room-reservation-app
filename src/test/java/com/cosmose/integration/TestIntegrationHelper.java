package com.cosmose.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;

import java.time.LocalDateTime;

/**
 * Created by damian on 26.08.18.
 */
@Ignore
public class TestIntegrationHelper {

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    protected String extractJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static final String resolveDate(int day, int month, int year, int hour) {
        return LocalDateTime.of(year, month, day, hour, 0).toString();
    }

}
