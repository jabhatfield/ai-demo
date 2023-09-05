package com.jonhatfield.aidemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpTokenizeResponse {
    private String[] tokens;
    private Map<String, Double> probabilities;
}
