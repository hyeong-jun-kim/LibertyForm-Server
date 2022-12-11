package com.example.libertyformapiserver.dto.flask.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostWordCloudDto {
    private List<String> textResponse;
}
