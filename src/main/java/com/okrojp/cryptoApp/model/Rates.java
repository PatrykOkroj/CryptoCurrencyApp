package com.okrojp.cryptoApp.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rates {
    Map<String, Double> details = new LinkedHashMap<>();

    @JsonAnySetter
    void setDetail(String key, Double value) {
        details.put(key, value);
    }

    public Map<String, Double> getDetails() {
        return details;
    }

}
