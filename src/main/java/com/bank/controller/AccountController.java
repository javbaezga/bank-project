package com.bank.controller;

import com.bank.dto.AccountDto;
import com.bank.dto.AccountResponseDto;
import com.bank.service.AccountService;
import com.bank.service.exception.ResourceBadRequestException;
import com.bank.validation.annotation.AccountNumber;
import com.bank.validation.group.Create;
import com.bank.validation.group.Patch;
import com.bank.validation.group.Update;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@Validated
public class AccountController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountService service;

    @Autowired
    public AccountController(final AccountService service) {
        this.service = service;
    }

    @GetMapping(value="/{id}")
    public AccountResponseDto getById(final @PathVariable("id") @NotNull @Min(1) Long id) {
        logger.debug("getById() - START: id = {}", id);
        return service.getById(id);
    }

    @GetMapping
    public List<AccountResponseDto> findByParam(
        final @RequestParam(value="numero", required=false) @AccountNumber String number,
        final @RequestParam(value="cliente_id", required=false) @Min(1) Long clientId
    ) {
        logger.debug("findByParam() - START: number = {}", number);
        if (number != null) {
            return List.of(service.findByNumber(number));
        }
        if (clientId != null) {
            return service.findByClientId(clientId);
        }
        throw new ResourceBadRequestException();
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> create(final @RequestBody @NotNull @Validated(Create.class) AccountDto dto) {
        logger.debug("create() - START");
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping(value="/{id}")
    public AccountResponseDto update(
        final @PathVariable("id") @NotNull @Min(1) Long id,
        final @RequestBody @NotNull @Validated(Update.class) AccountDto dto
    ) {
        logger.debug("update() - START: id = {}", id);
        return service.update(id, dto);
    }

    @PatchMapping(value="/{id}")
    public AccountResponseDto patch(
        final @PathVariable("id") @NotNull @Min(1) Long id,
        final @RequestBody @NotNull @Validated(Patch.class) AccountDto dto
    ) {
        logger.debug("patch() - START: id = {}", id);
        return service.update(id, dto);
    }

    @DeleteMapping(value="/{id}")
    public AccountResponseDto delete(final @PathVariable("id") @NotNull @Min(1) Long id) {
        logger.debug("delete() - START: id = {}", id);
        return service.delete(id);
    }
}
