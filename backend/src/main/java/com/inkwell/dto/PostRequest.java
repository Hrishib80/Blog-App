package com.inkwell.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    @NotBlank
    private String title;

    private String subtitle;

    @NotBlank
    private String content;

    private String coverImage;

    private List<String> tagNames;

    @Builder.Default
    private boolean published = false;
}
