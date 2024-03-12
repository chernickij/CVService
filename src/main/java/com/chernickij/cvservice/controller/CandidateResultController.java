package com.chernickij.cvservice.controller;

import com.chernickij.cvservice.dto.CandidateResultDto;
import com.chernickij.cvservice.dto.SaveCandidateResultDto;
import com.chernickij.cvservice.dto.SaveTestDto;
import com.chernickij.cvservice.dto.TestDto;
import com.chernickij.cvservice.service.CandidateResultService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidate-results")
public class CandidateResultController {

    private final CandidateResultService candidateResultService;

    @GetMapping
    public ResponseEntity<List<CandidateResultDto>> getAll(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit
    ) {
        List<CandidateResultDto> candidateResults = candidateResultService.getAll(offset, limit);

        if (candidateResults.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(candidateResults, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResultDto> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(candidateResultService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CandidateResultDto> create(@RequestBody SaveCandidateResultDto saveCandidateResultDto) {
        return new ResponseEntity<>(candidateResultService.save(saveCandidateResultDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateResultDto> update(@PathVariable("id") Long id, @RequestBody SaveCandidateResultDto saveCandidateResultDto) {
        return new ResponseEntity<>(candidateResultService.update(id, saveCandidateResultDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TestDto> delete(@PathVariable("id") Long id) {
        candidateResultService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
