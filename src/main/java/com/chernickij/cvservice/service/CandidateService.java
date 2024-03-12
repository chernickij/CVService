package com.chernickij.cvservice.service;

import com.chernickij.cvservice.dto.CandidateDto;
import com.chernickij.cvservice.dto.SaveCandidateDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateService {
    List<CandidateDto> getAll(Integer offset, Integer limit);

    CandidateDto getById(Long id);

    CandidateDto save(SaveCandidateDto saveCandidateDto);

    void savePhoto(Long id, MultipartFile photoFile);

    Resource getPhoto(Long id);

    void saveCv(Long id, MultipartFile cvFile);

    Resource getCv(Long id);

    CandidateDto update(Long id, SaveCandidateDto saveCandidateDto);

    void delete(Long id);
}
