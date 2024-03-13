package com.chernickij.cvservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Test data")
public class TestDto {
    @Schema(description = "Test ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Test name")
    private String name;
    @Schema(description = "Test description")
    private String description;
    @Schema(description = "List of test directions")
    private List<DirectionDto> directions;
}
