package com.jonhatfield.aidemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpTokenizeResponse {
    private String[] tokens;
    List<ImmutablePair<String, Double>> probabilities;
}
