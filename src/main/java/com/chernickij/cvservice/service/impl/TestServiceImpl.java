package com.chernickij.cvservice.service.impl;

import com.chernickij.cvservice.dto.SaveTestDto;
import com.chernickij.cvservice.dto.TestDto;
import com.chernickij.cvservice.exception.ResourceAlreadyExist;
import com.chernickij.cvservice.exception.ResourceNotFoundException;
import com.chernickij.cvservice.mapper.DirectionMapper;
import com.chernickij.cvservice.mapper.TestMapper;
import com.chernickij.cvservice.model.Test;
import com.chernickij.cvservice.repository.DirectionRepository;
import com.chernickij.cvservice.repository.TestRepository;
import com.chernickij.cvservice.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.text.MessageFormat.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final DirectionRepository directionRepository;
    private final TestMapper testMapper;
    private final DirectionMapper directionMapper;

    @Override
    @Transactional
    public List<TestDto> getAll(Integer offset, Integer limit) {
        log.debug("Executing the testService.getAll method with offset = %d and limit = %d".formatted(offset, limit));

        Page<Test> pagedResult = testRepository.findAll(PageRequest.of(offset, limit));

        return pagedResult.hasContent()
                ? pagedResult.getContent().stream().map(testMapper::mapToTestDto).toList()
                : new ArrayList<>();
    }

    @Override
    @Transactional
    public TestDto getById(Long id) {
        log.debug("Executing the testService.getById method with id {}", id);

        return testRepository.findById(id)
                .map(testMapper::mapToTestDto)
                .orElseThrow(() -> new ResourceNotFoundException(format("Test with id {0} not found ", id)));
    }

    @Override
    @Transactional
    public TestDto save(SaveTestDto saveTestDto) {
        log.debug("Executing the roleService.save method for test with name: %s".formatted(saveTestDto.getName()));

        testRepository.findByName(saveTestDto.getName()).ifPresent(privilege -> {
            throw new ResourceAlreadyExist(MessageFormat.format("Test with name {0} already exist", saveTestDto.getName()));
        });

        saveTestDto.getDirections().forEach(directionDto -> directionRepository.findByName(directionDto.getName())
                .orElseThrow(() -> new ResourceNotFoundException(format("Direction with name {0} not found ", directionDto.getName()))));

        return testMapper.mapToTestDto(testRepository.save(Test.builder()
                .name(saveTestDto.getName())
                .description(saveTestDto.getDescription())
                .directions(saveTestDto.getDirections().stream().map(directionMapper::mapToDirection).toList())
                .created(new Date())
                .build()));
    }

    @Override
    @Transactional
    public TestDto update(Long id, SaveTestDto saveTestDto) {
        log.debug("Executing the testService.save method for test with id: %s".formatted(id));

        Test test = testRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Test with id {0} not found ", id)));

        saveTestDto.getDirections().forEach(directionDto -> directionRepository.findByName(directionDto.getName())
                .orElseThrow(() -> new ResourceNotFoundException(format("Direction with name {0} not found ", directionDto.getName()))));

        test.setName(saveTestDto.getName());
        test.setDescription(saveTestDto.getDescription());
        test.setDirections(saveTestDto.getDirections().stream().map(directionMapper::mapToDirection).toList());
        test.setUpdated(new Date());

        return testMapper.mapToTestDto(testRepository.save(test));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Executing the testService.delete method for test with id: %s".formatted(id));

        Test test = testRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Test with id {0} not found ", id)));
        testRepository.delete(test);
    }
}
