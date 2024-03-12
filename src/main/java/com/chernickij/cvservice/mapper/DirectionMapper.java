package com.chernickij.cvservice.mapper;

import com.chernickij.cvservice.dto.DirectionDto;
import com.chernickij.cvservice.model.Direction;
import org.mapstruct.Mapper;

@Mapper
public interface DirectionMapper {

    Direction mapToDirection(DirectionDto directionDto);

    DirectionDto mapToDirectionDto(Direction direction);
}
