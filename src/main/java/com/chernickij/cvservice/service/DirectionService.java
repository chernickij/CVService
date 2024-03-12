package com.chernickij.cvservice.service;

import com.chernickij.cvservice.dto.DirectionDto;
import com.chernickij.cvservice.dto.SaveDirectionDto;

import java.util.List;

public interface DirectionService {
    List<DirectionDto> getAll(Integer offset, Integer limit);

    DirectionDto getById(Long id);

    DirectionDto save(SaveDirectionDto saveDirectionDto);

    DirectionDto update(Long id, SaveDirectionDto saveDirectionDto);

    void delete(Long id);
}
