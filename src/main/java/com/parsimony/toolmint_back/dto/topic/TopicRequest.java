package com.parsimony.toolmint_back.dto.topic;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TopicRequest {

    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String emoji;

}
