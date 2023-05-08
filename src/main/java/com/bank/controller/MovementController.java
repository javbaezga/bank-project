package com.bank.controller;

import com.bank.dto.MovementDto;
import com.bank.dto.MovementResponseDto;
import com.bank.service.MovementService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimientos")
@Validated
public class MovementController {
    private static final Logger logger = LoggerFactory.getLogger(MovementController.class);
    private final MovementService service;

    @Autowired
    public MovementController(final MovementService service) {
        this.service = service;
    }

    @GetMapping(value="/{id}")
    public MovementResponseDto getById(final @PathVariable("id") @NotNull @Min(1) Long id) {
        logger.debug("getById() - START: id = {}", id);
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<MovementResponseDto> create(final @RequestBody @NotNull MovementDto dto) {
        logger.debug("create() - START");
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }
}
