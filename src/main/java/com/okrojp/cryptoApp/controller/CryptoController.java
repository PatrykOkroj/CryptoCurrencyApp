package com.okrojp.cryptoApp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.okrojp.cryptoApp.service.CryptoServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("currencies")
@RequiredArgsConstructor
public class CryptoController {

    @Getter
    private final CryptoServiceImpl service;


    @GetMapping("/{currency}")
    public ResponseEntity<?> getPrices(@PathVariable String currency) {
        try {
            if (service.getCryptoInfo(currency).getRates() == null && service.getCryptoInfo(currency).getTarget() == null) {
                return new ResponseEntity<>("{\"message\" : \"Cannot find typed currency\"}", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(service.getCryptoInfo(currency));
    }
    
    @PostMapping(value = "/exchange")
    public ResponseEntity<String> createExchange(@RequestBody ObjectNode json) {
        List<String> splitJsonList = new ArrayList<>();
        if (json.get("from") == null || json.get("to") == null || json.get("amount") == null) {
            return new ResponseEntity<>("{\"message\" : \"Cannot find some arguments\"}", HttpStatus.NOT_FOUND);
        } else {
            if (json.get("to").isArray()) {
                for (JsonNode jsn : json.get("to")) {
                    splitJsonList.add(jsn.asText());
                }
            } else {
                splitJsonList.add(json.get("to").asText());
            }
        }
        return ResponseEntity.ok(service.getCurrentPrice(json.get("from").asText(), splitJsonList, Double.parseDouble(json.get("amount").asText())));
    }
}
