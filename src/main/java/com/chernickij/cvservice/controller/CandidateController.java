package com.chernickij.cvservice.controller;

import com.chernickij.cvservice.dto.CandidateDto;
import com.chernickij.cvservice.dto.SaveCandidateDto;
import com.chernickij.cvservice.dto.SaveTestDto;
import com.chernickij.cvservice.dto.TestDto;
import com.chernickij.cvservice.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping
    @Operation(summary = "Get all", description = "Get list of all candidates")
    public ResponseEntity<List<CandidateDto>> getAll(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit
    ) {
        List<CandidateDto> candidates = candidateService.getAll(offset, limit);

        if (candidates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get by ID", description = "Get candidate by his ID")
    public ResponseEntity<CandidateDto> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(candidateService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create candidate", description = "Create new candidate")
    public ResponseEntity<CandidateDto> create(@RequestBody SaveCandidateDto saveCandidateDto) {
        return new ResponseEntity<>(candidateService.save(saveCandidateDto), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/photo")
    @Operation(summary = "Save photo", description = "Add candidate photo")
    public ResponseEntity<?> savePhoto(@PathVariable Long id, @RequestParam("photo") MultipartFile file) {
        candidateService.savePhoto(id, file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/photo")
    @Operation(summary = "Serve photo", description = "Get candidate photo")
    public ResponseEntity<Resource> servePhoto(@PathVariable Long id) {
        Resource file = candidateService.getPhoto(id);

        if (file == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/{id}/cv")
    @Operation(summary = "Save CV", description = "Add candidate CV")
    public ResponseEntity<?> saveCv(@PathVariable Long id, @RequestParam("cv") MultipartFile file) {
        candidateService.saveCv(id, file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/cv")
    @Operation(summary = "Serve CV", description = "Get Candidate CV")
    public ResponseEntity<Resource> serveCv(@PathVariable Long id) {
        Resource file = candidateService.getCv(id);

        if (file == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update candidate", description = "Update candidate information by ID")
    public ResponseEntity<CandidateDto> update(@PathVariable("id") Long id, @RequestBody SaveCandidateDto saveCandidateDto) {
        return new ResponseEntity<>(candidateService.update(id, saveCandidateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete candidate", description = "Delete candidate by ID")
    public ResponseEntity<CandidateDto> delete(@PathVariable("id") Long id) {
        candidateService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
