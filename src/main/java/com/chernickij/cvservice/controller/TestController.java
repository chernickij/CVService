package com.chernickij.cvservice.controller;

import com.chernickij.cvservice.dto.SaveTestDto;
import com.chernickij.cvservice.dto.TestDto;
import com.chernickij.cvservice.service.TestService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tests")
public class TestController {
    
    private final TestService testService;

    @GetMapping
    public ResponseEntity<List<TestDto>> getAll(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit
    ) {
        List<TestDto> tests = testService.getAll(offset, limit);

        if (tests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tests, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestDto> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(testService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TestDto> create(@RequestBody SaveTestDto saveTestDto) {
        return new ResponseEntity<>(testService.save(saveTestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestDto> update(@PathVariable("id") Long id, @RequestBody SaveTestDto saveTestDto) {
        return new ResponseEntity<>(testService.update(id, saveTestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TestDto> delete(@PathVariable("id") Long id) {
        testService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
