package com.chernickij.cvservice.service.impl;

import com.chernickij.cvservice.dto.DirectionDto;
import com.chernickij.cvservice.dto.SaveDirectionDto;
import com.chernickij.cvservice.exception.ResourceAlreadyExist;
import com.chernickij.cvservice.exception.ResourceNotFoundException;
import com.chernickij.cvservice.mapper.DirectionMapper;
import com.chernickij.cvservice.model.Direction;
import com.chernickij.cvservice.repository.DirectionRepository;
import com.chernickij.cvservice.service.DirectionService;
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
public class DirectionServiceImpl implements DirectionService {

    private final DirectionRepository directionRepository;
    private final DirectionMapper directionMapper;
    
    @Override
    @Transactional
    public List<DirectionDto> getAll(Integer offset, Integer limit) {
        log.debug("Executing the directionService.getAll method with offset = %d and limit = %d".formatted(offset, limit));

        Page<Direction> pagedResult = directionRepository.findAll(PageRequest.of(offset, limit));

        return pagedResult.hasContent()
                ? pagedResult.getContent().stream().map(directionMapper::mapToDirectionDto).toList()
                : new ArrayList<>();
    }

    @Override
    @Transactional
    public DirectionDto getById(Long id) {
        log.debug("Executing the directionService.getById method with id {}", id);

        return directionRepository.findById(id)
                .map(directionMapper::mapToDirectionDto)
                .orElseThrow(() -> new ResourceNotFoundException(format("Direction with id {0} not found ", id)));
    }

    @Override
    @Transactional
    public DirectionDto save(SaveDirectionDto saveDirectionDto) {
        log.debug("Executing the directionService.save method for direction with name: %s".formatted(saveDirectionDto.getName()));

        directionRepository.findByName(saveDirectionDto.getName())
                .ifPresent(direction -> {
                    throw new ResourceAlreadyExist(MessageFormat.format("Direction with name {0} already exist", direction.getName()));
                });

        return directionMapper.mapToDirectionDto(directionRepository.save(Direction.builder()
                .name(saveDirectionDto.getName())
                .description(saveDirectionDto.getDescription())
                .created(new Date())
                .build()));
    }

    @Override
    @Transactional
    public DirectionDto update(Long id, SaveDirectionDto saveDirectionDto) {
        log.debug("Executing the directionService.save method for direction with id: %s".formatted(id));

        Direction direction = directionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Direction with id {0} not found ", id)));

        direction.setName(saveDirectionDto.getName());
        direction.setDescription(saveDirectionDto.getDescription());
        direction.setUpdated(new Date());

        return directionMapper.mapToDirectionDto(directionRepository.save(direction));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Executing the directionService.delete method for direction with id: %s".formatted(id));

        Direction direction = directionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Direction with id {0} not found ", id)));
        directionRepository.delete(direction);
    }
}
