package com.chernickij.cvservice.service.impl;

import com.chernickij.cvservice.exception.BadRequestException;
import com.chernickij.cvservice.model.Test;
import com.chernickij.cvservice.utils.FileType;
import com.chernickij.cvservice.dto.CandidateDto;
import com.chernickij.cvservice.dto.SaveCandidateDto;
import com.chernickij.cvservice.exception.ResourceNotFoundException;
import com.chernickij.cvservice.mapper.CandidateMapper;
import com.chernickij.cvservice.mapper.DirectionMapper;
import com.chernickij.cvservice.model.Candidate;
import com.chernickij.cvservice.repository.CandidateRepository;
import com.chernickij.cvservice.repository.DirectionRepository;
import com.chernickij.cvservice.service.CandidateService;
import com.chernickij.cvservice.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.text.MessageFormat.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    @Value("${file-storage.directory.image}")
    private String imagePath;
    @Value("${file-storage.directory.cv}")
    private String cvPath;

    private final FileStorageService fileStorageService;
    private final CandidateRepository candidateRepository;
    private final DirectionRepository directionRepository;
    private final CandidateMapper candidateMapper;
    private final DirectionMapper directionMapper;

    @Override
    @Transactional
    public List<CandidateDto> getAll(Integer offset, Integer limit) {
        log.debug("Executing the candidateService.getAll method with offset = %d and limit = %d".formatted(offset, limit));

        Page<Candidate> pagedResult = candidateRepository.findAll(PageRequest.of(offset, limit));
        return pagedResult.hasContent()
                ? pagedResult.getContent().stream().map(candidateMapper::mapToCandidatedto).toList()
                : new ArrayList<>();
    }

    @Override
    @Transactional
    public CandidateDto getById(Long id) {
        log.debug("Executing the candidateService.getById method with id {}", id);

        return candidateRepository.findById(id)
                .map(candidateMapper::mapToCandidatedto)
                .orElseThrow(() -> new ResourceNotFoundException(format("Candidate with id {0} not found ", id)));
    }

    @Override
    @Transactional
    public CandidateDto save(SaveCandidateDto saveCandidateDto) {
        log.debug("Executing the candidateService.save method for Candidate with name: %s".formatted(saveCandidateDto.getName()));

        saveCandidateDto.getDirections().forEach(directionDto -> directionRepository.findByName(directionDto.getName())
                .orElseThrow(() -> new ResourceNotFoundException(format("Direction with name {0} not found ", directionDto.getName()))));

        return candidateMapper.mapToCandidatedto(candidateRepository.save(Candidate.builder()
                .name(saveCandidateDto.getName())
                .surname(saveCandidateDto.getSurname())
                .patronymic(saveCandidateDto.getPatronymic())
                .description(saveCandidateDto.getDescription())
                .directions(saveCandidateDto.getDirections().stream().map(directionMapper::mapToDirection).toList())
                .created(new Date())
                .build()));
    }

    @Override
    public void savePhoto(Long id, MultipartFile photoFile) {
        log.debug("Executing the candidateService.savePhoto method for candidate with id {}", id);

        if(photoFile.getOriginalFilename() == null){
            throw new BadRequestException("Wrong file name");
        }

        candidateRepository.findById(id)
                .map(candidate -> {
                    String fileName = getFileName(FileType.CANDIDATE_PHOTO, id, photoFile.getOriginalFilename());
                    fileStorageService.saveFile(photoFile, fileName);
                    candidate.setPhotoFile(fileName);
                    return candidateRepository.save(candidate);
                }
        ).orElseThrow(() -> new ResourceNotFoundException(format("Candidate with id {0} not found ", id)));
    }

    @Override
    public Resource getPhoto(Long id) {
        log.debug("Executing the candidateService.getPhoto method for candidate with id {}", id);

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Candidate with id {0} not found ", id)));
        return fileStorageService.getFile(candidate.getPhotoFile());
    }

    @Override
    public void saveCv(Long id, MultipartFile cvFile) {
        log.debug("Executing the candidateService.saveCv method for candidate with id {}", id);

        if(cvFile.getOriginalFilename() == null){
            throw new BadRequestException("Wrong file name");
        }

        candidateRepository.findById(id)
                .map(candidate -> {
                            String fileName = getFileName(FileType.CV, id, cvFile.getOriginalFilename());
                            fileStorageService.saveFile(cvFile, fileName);
                            candidate.setCvFile(fileName);
                            return candidateRepository.save(candidate);
                        }
                ).orElseThrow(() -> new ResourceNotFoundException(format("Candidate with id {0} not found ", id)));
    }

    @Override
    public Resource getCv(Long id) {
        log.debug("Executing the candidateService.getCv method for candidate with id {}", id);

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Candidate with id {0} not found ", id)));
        return fileStorageService.getFile(candidate.getCvFile());
    }

    @Override
    @Transactional
    public CandidateDto update(Long id, SaveCandidateDto saveCandidateDto) {
        log.debug("Executing the candidateService.save method for test with id: %s".formatted(id));

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Candidate with id {0} not found ", id)));

        saveCandidateDto.getDirections().forEach(directionDto -> directionRepository.findByName(directionDto.getName())
                .orElseThrow(() -> new ResourceNotFoundException(format("Direction with name {0} not found ", directionDto.getName()))));

        candidate.setName(saveCandidateDto.getName());
        candidate.setSurname(saveCandidateDto.getSurname());
        candidate.setPatronymic(saveCandidateDto.getPatronymic());
        candidate.setDescription(saveCandidateDto.getDescription());
        candidate.setDirections(saveCandidateDto.getDirections().stream().map(directionMapper::mapToDirection).toList());
        candidate.setUpdated(new Date());

        return candidateMapper.mapToCandidatedto(candidateRepository.save(candidate));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Executing the candidateService.delete method for test with id: %s".formatted(id));

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Candidate with id {0} not found ", id)));
        candidateRepository.delete(candidate);
    }

    private String getFileName(FileType type, Long candidateId, String fileName) {
        String path = type == FileType.CANDIDATE_PHOTO ? imagePath : cvPath;
        return path + "\\" + candidateId.toString() + fileName.substring(fileName.lastIndexOf("."));
    }
}
