package com.jonhatfield.aidemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OpenNlpRequest {

    String message;
    String model;
}
