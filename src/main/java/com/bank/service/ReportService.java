package com.bank.service;

import com.bank.dto.BankStatementResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    /**
     * Gets bank statement by client.
     * @param clientId Client ID.
     * @param startDate Start date.
     * @param endDate End date.
     * @param page Page.
     * @param size Page size.
     * @return List of bank statement response DTO.
     */
    List<BankStatementResponseDto> getBankStatement(
        Long clientId,
        LocalDate startDate,
        LocalDate endDate,
        Integer page,
        Integer size
    );
}
