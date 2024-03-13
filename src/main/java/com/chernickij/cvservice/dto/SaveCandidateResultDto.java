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
@Schema(description = "Candidate test result data for creating or updating")
public class SaveCandidateResultDto {
    @Schema(description = "Candidate test result mark")
    private int mark;
    @Schema(description = "Candidate information ")
    private CandidateDto candidate;
    @Schema(description = "Test information")
    private TestDto test;
}
