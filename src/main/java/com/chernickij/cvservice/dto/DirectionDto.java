package com.chernickij.cvservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Direction data")
public class DirectionDto {
    @Schema(description = "Direction ID", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(description = "Direction name")
    private String name;
    @Schema(description = "Direction description")
    private String description;
}
