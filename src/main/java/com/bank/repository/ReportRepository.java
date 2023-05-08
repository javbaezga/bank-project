package com.bank.repository;

import com.bank.dto.BankStatementResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ReportRepository {
    Page<BankStatementResponseDto> getBankStatement(
        Long clientId,
        LocalDate startDate,
        LocalDate endDate,
        Pageable pageable
    );
}
