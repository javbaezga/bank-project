package com.bank.controller;

import com.bank.dto.ClientDto;
import com.bank.dto.ClientResponseDto;
import com.bank.service.ClientService;
import com.bank.validation.annotation.IdNumber;
import com.bank.validation.group.Create;
import com.bank.validation.group.Patch;
import com.bank.validation.group.Update;
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
@RequestMapping("/clientes")
@Validated
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final ClientService clientService;

    @Autowired
    public ClientController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value="/{id}")
    public ClientResponseDto getById(final @PathVariable("id") @NotNull @Min(1) Long id) {
        logger.debug("getById() - START: id = {}", id);
        return clientService.getById(id);
    }

    @GetMapping
    public ClientResponseDto findByIdNumber(final @RequestParam("identificacion") @NotNull @IdNumber String idNumber) {
        logger.debug("findByIdNumber() - START: idNumber = {}", idNumber);
        return clientService.findByIdNumber(idNumber);
    }

    @PostMapping
    public ResponseEntity<ClientResponseDto> create(final @RequestBody @NotNull @Validated(Create.class) ClientDto dto) {
        logger.debug("create() - START");
        return new ResponseEntity<>(clientService.save(dto), HttpStatus.CREATED);
    }

    @PutMapping(value="/{id}")
    public ClientResponseDto update(
        final @PathVariable("id") @NotNull @Min(1) Long id,
        final @RequestBody @NotNull @Validated(Update.class) ClientDto dto
    ) {
        logger.debug("update() - START: id = {}", id);
        return clientService.update(id, dto);
    }

    @PatchMapping(value="/{id}")
    public ClientResponseDto patch(
        final @PathVariable("id") @NotNull @Min(1) Long id,
        final @RequestBody @NotNull @Validated(Patch.class) ClientDto dto
    ) {
        logger.debug("patch() - START: id = {}", id);
        return clientService.update(id, dto);
    }

    @DeleteMapping(value="/{id}")
    public ClientResponseDto delete(final @PathVariable("id") @NotNull @Min(1) Long id) {
        logger.debug("delete() - START: id = {}", id);
        return clientService.delete(id);
    }
}
