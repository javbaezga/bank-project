package com.bank.controller;

import com.bank.dto.BankStatementResponseDto;
import com.bank.service.ReportService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes")
@Validated
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    private final ReportService service;

    @Autowired
    public ReportController(final ReportService service) {
        this.service = service;
    }

    @GetMapping(value="/estado-de-cuenta/{id}")
    public List<BankStatementResponseDto> getBankStatement(
        final @PathVariable("id") @NotNull @Min(1) Long clientId,
        final @RequestParam("fecha_inicio") @NotNull LocalDate startDate,
        final @RequestParam("fecha_fin") @NotNull LocalDate endDate,
        final @RequestParam("pagina") @NotNull @Min(0) Integer page,
        final @RequestParam("tamano") @NotNull @Min(1) @Max(100) Integer size
    ) {
        logger.debug(
            "getBankStatement() - START: clientId = {}, startDate = {}, endDate = {}, page = {}, size = {}",
            clientId,
            startDate,
            endDate,
            page,
            size
        );
        return service.getBankStatement(
            clientId,
            startDate,
            endDate,
            page,
            size
        );
    }
}
