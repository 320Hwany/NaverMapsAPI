package com.mapapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapapi.domain.Address;
import com.mapapi.dto.Graph;
import com.mapapi.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class TestController {

    @Value("${naver.id}")
    private String naverId;

    @Value("${naver.secret}")
    private String naverSecret;

    @Value("${naver.url}")
    private String naverUrl;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    private final AddressService addressService;

    @PostMapping("/save")
    public void save(@RequestBody Graph graph) {
        addressService.save(graph);
    }

    @GetMapping("/hello")
    public ResponseEntity<Graph> testApiGet(@RequestParam String query) throws JsonProcessingException {

        HttpEntity request = setHttpRequestForMapsAPI();
        String requestUrl = naverUrl + query;
        ResponseEntity<String> response = getStringResponseEntity(request, requestUrl);

        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        JsonNode addresses = jsonNode.path("addresses");
        String longitude = addresses.findPath("x").textValue();
        String latitude = addresses.findPath("y").textValue();

        Graph graph = Graph.getFromXAndY(longitude, latitude);
        return ResponseEntity.ok(graph);
    }

    @PostMapping("/distance")
    public ResponseEntity<List<Address>> getDistance(@RequestBody Graph graph) {
        addressService.calculateDistance(graph);
        List<Address> addressList = addressService.findOrderByDistance();
        return ResponseEntity.ok(addressList);
    }

    private ResponseEntity<String> getStringResponseEntity(HttpEntity request, String requestUrl) {
        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                request,
                String.class
        );
        return response;
    }

    private HttpEntity setHttpRequestForMapsAPI() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", naverId);
        headers.set("X-NCP-APIGW-API-KEY", naverSecret);
        HttpEntity request = new HttpEntity(headers);
        return request;
    }
}
