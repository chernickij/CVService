package com.chernickij.cvservice.mapper;

import com.chernickij.cvservice.dto.CandidateDto;
import com.chernickij.cvservice.model.Candidate;
import org.mapstruct.Mapper;

@Mapper
public interface CandidateMapper {

    CandidateDto mapToCandidatedto(Candidate candidate);
}
