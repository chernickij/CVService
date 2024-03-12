package com.chernickij.cvservice.dto;

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
public class CandidateDto {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String description;
    private List<DirectionDto> directions;
}
