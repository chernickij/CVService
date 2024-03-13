package com.chernickij.cvservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Candidate date for creating or updating")
public class SaveCandidateDto {
    @Schema(description = "Candidate name")
    private String name;
    @Schema(description = "Candidate surname")
    private String surname;
    @Schema(description = "Candidate patronymic")
    private String patronymic;
    @Schema(description = "Candidate description")
    private String description;
    @Schema(description = "List of candidate directions")
    private List<DirectionDto> directions;
}
