package com.chernickij.cvservice.mapper;

import com.chernickij.cvservice.dto.CandidateResultDto;
import com.chernickij.cvservice.model.CandidateResult;
import org.mapstruct.Mapper;

@Mapper
public interface CandidateResultMapper {

    CandidateResultDto mapToCandidateResultDto(CandidateResult candidateResult);
}
