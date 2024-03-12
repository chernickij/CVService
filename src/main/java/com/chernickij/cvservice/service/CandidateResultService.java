package com.chernickij.cvservice.service;

import com.chernickij.cvservice.dto.CandidateResultDto;
import com.chernickij.cvservice.dto.SaveCandidateResultDto;

import java.util.List;

public interface CandidateResultService {
    List<CandidateResultDto> getAll(Integer offset, Integer limit);

    CandidateResultDto getById(Long id);

    CandidateResultDto save(SaveCandidateResultDto saveCandidateResultDto);

    CandidateResultDto update(Long id, SaveCandidateResultDto saveCandidateResultDto);

    void delete(Long id);
}
