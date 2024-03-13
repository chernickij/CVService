package com.chernickij.cvservice.controller;

import com.chernickij.cvservice.dto.DirectionDto;
import com.chernickij.cvservice.dto.SaveDirectionDto;
import com.chernickij.cvservice.model.Direction;
import com.chernickij.cvservice.service.DirectionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/directions")
public class DirectionController {

    private final DirectionService directionService;

    @GetMapping
    @Operation(summary = "Get all", description = "Get list of all directions")
    public ResponseEntity<List<DirectionDto>> getAll(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit
    ) {
        List<DirectionDto> directions = directionService.getAll(offset, limit);

        if (directions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(directions, HttpStatus.OK);
    }

    @GetMapping("{/id}")
    @Operation(summary = "Get by ID", description = "Get direction by ID")
    public ResponseEntity<DirectionDto> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(directionService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create direction", description = "Create new direction")
    public ResponseEntity<DirectionDto> create(@RequestBody SaveDirectionDto saveDirectionDto) {
        return new ResponseEntity<>(directionService.save(saveDirectionDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update direction", description = "Update direction information by ID")
    public ResponseEntity<DirectionDto> update(@PathVariable("id") Long id, @RequestBody SaveDirectionDto saveDirectionDto) {
        return new ResponseEntity<>(directionService.update(id, saveDirectionDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete direction", description = "Delete direction by ID")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        directionService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
