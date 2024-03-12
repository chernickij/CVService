package com.chernickij.cvservice.mapper;

import com.chernickij.cvservice.dto.TestDto;
import com.chernickij.cvservice.model.Test;
import org.mapstruct.Mapper;

@Mapper
public interface TestMapper {
    
    Test mapToTest(TestDto testDto);
    
    TestDto mapToTestDto(Test test);
}
