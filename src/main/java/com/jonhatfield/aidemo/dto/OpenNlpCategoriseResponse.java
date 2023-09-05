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
public class OpenNlpCategoriseResponse {
    private String category;
    private String reply;
    private Map<String, Double> probabilities;
}
