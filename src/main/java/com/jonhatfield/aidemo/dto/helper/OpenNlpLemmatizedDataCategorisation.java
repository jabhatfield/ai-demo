package com.jonhatfield.aidemo.dto.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenNlpLemmatizedDataCategorisation {
    private String category;
    private List<String> lemmatizedPhrases;
}