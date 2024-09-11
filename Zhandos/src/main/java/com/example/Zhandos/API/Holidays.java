package com.example.Zhandos.API;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Holidays {
    public static List<LocalDate> holiday() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://date.nager.at/api/v3/publicholidays/2024/AT";

        ResponseEntity<List<Holiday>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Holiday>>() {}
        );

        List<Holiday> holidays = response.getBody();



        List<LocalDate> dates = new ArrayList<>();

        for (Holiday holiday : holidays) {
            dates.add(holiday.getDate());
        }

        return dates;
    }
}
