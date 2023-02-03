package com.mapapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Templates;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Value("${naver.id}")
    private String naverId;

    @Value("${naver.secret}")
    private String naverSecret;

    private List<String> addresses = new ArrayList<>();

    @GetMapping("/hello")
    public ResponseEntity<String> testApi(@RequestParam String query) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", naverId);
        headers.set("X-NCP-APIGW-API-KEY", naverSecret);

        HttpEntity request = new HttpEntity(headers);
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode"
                + "?query=" + query;

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
        );

        return response;
    }
}
