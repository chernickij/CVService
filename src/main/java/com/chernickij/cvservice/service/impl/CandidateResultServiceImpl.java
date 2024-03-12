package com.chernickij.cvservice.service.impl;

import com.chernickij.cvservice.dto.CandidateResultDto;
import com.chernickij.cvservice.dto.SaveCandidateResultDto;
import com.chernickij.cvservice.exception.ResourceNotFoundException;
import com.chernickij.cvservice.mapper.CandidateResultMapper;
import com.chernickij.cvservice.model.Candidate;
import com.chernickij.cvservice.model.CandidateResult;
import com.chernickij.cvservice.model.Test;
import com.chernickij.cvservice.repository.CandidateRepository;
import com.chernickij.cvservice.repository.CandidateResultRepository;
import com.chernickij.cvservice.repository.TestRepository;
import com.chernickij.cvservice.service.CandidateResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.text.MessageFormat.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidateResultServiceImpl implements CandidateResultService {

    private final CandidateResultRepository candidateResultRepository;
    private final TestRepository testRepository;
    private final CandidateRepository candidateRepository;
    private final CandidateResultMapper candidateResultMapper;

    @Override
    @Transactional
    public List<CandidateResultDto> getAll(Integer offset, Integer limit) {
        log.debug("Executing the candidateResultService.getAll method with offset = %d and limit = %d".formatted(offset, limit));

        Page<CandidateResult> pagedResult = candidateResultRepository.findAll(PageRequest.of(offset, limit));

        return pagedResult.hasContent()
                ? pagedResult.getContent().stream().map(candidateResultMapper::mapToCandidateResultDto).toList()
                : new ArrayList<>();
    }

    @Override
    @Transactional
    public CandidateResultDto getById(Long id) {
        log.debug("Executing the candidateResultService.getById method with id {}", id);

        return candidateResultRepository.findById(id)
                .map(candidateResultMapper::mapToCandidateResultDto)
                .orElseThrow(() -> new ResourceNotFoundException(format("Candidate Result with id {0} not found ", id)));
    }

    @Override
    @Transactional
    public CandidateResultDto save(SaveCandidateResultDto saveCandidateResultDto) {
        log.debug("Executing the candidateResultService.save method for candidate with name: %s".formatted(saveCandidateResultDto.getCandidate().getName()));

        Test test = testRepository.findById(saveCandidateResultDto.getTest().getId())
                .orElseThrow(() -> new ResourceNotFoundException(format("Test with id {0} not found ", saveCandidateResultDto.getTest().getId())));

        Candidate candidate = candidateRepository.findById(saveCandidateResultDto.getCandidate().getId())
                .orElseThrow(() -> new ResourceNotFoundException(format("Candidate with id {0} not found ", saveCandidateResultDto.getCandidate().getId())));

        return candidateResultMapper.mapToCandidateResultDto(candidateResultRepository.save(CandidateResult.builder()
                .date(new Date())
                .mark(saveCandidateResultDto.getMark())
                .test(test)
                .candidate(candidate)
                .created(new Date())
                .build()));
    }

    @Override
    @Transactional
    public CandidateResultDto update(Long id, SaveCandidateResultDto saveCandidateResultDto) {
        log.debug("Executing the candidateResultService.save method for candidate result with id: %s".formatted(id));

        CandidateResult candidateResult = candidateResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Candidate Result with id {0} not found ", id)));

        Test test = testRepository.findById(saveCandidateResultDto.getTest().getId())
                .orElseThrow(() -> new ResourceNotFoundException(format("Test with id {0} not found ", saveCandidateResultDto.getTest().getId())));

        Candidate candidate = candidateRepository.findById(saveCandidateResultDto.getCandidate().getId())
                .orElseThrow(() -> new ResourceNotFoundException(format("Candidate with id {0} not found ", saveCandidateResultDto.getCandidate().getId())));

        candidateResult.setMark(saveCandidateResultDto.getMark());
        candidateResult.setTest(test);
        candidateResult.setCandidate(candidate);
        candidateResult.setUpdated(new Date());

        return candidateResultMapper.mapToCandidateResultDto(candidateResultRepository.save(candidateResult));    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Executing the candidateResultService.delete method for candidate result with id: %s".formatted(id));

        CandidateResult candidateResult = candidateResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Candidate result with id {0} not found ", id)));
        candidateResultRepository.delete(candidateResult);
    }
}
