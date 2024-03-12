package com.chernickij.cvservice.service;

import com.chernickij.cvservice.dto.SaveTestDto;
import com.chernickij.cvservice.dto.TestDto;

import java.util.List;

public interface TestService {
    List<TestDto> getAll(Integer offset, Integer limit);

    TestDto getById(Long id);

    TestDto save(SaveTestDto saveTestDto);

    TestDto update(Long id, SaveTestDto saveTestDto);

    void delete(Long id);
}
