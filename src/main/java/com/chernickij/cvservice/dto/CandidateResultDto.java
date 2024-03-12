package com.chernickij.cvservice.dto;

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
public class CandidateResultDto {
    private Long id;
    private int mark;
    private CandidateDto candidate;
    private TestDto test;
}
