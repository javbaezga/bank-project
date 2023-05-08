package com.bank.service.impl;

import com.bank.dto.BankStatementResponseDto;
import com.bank.repository.ReportRepository;
import com.bank.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository repository;

    @Autowired
    public ReportServiceImpl(final ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BankStatementResponseDto> getBankStatement(
        final Long clientId,
        final LocalDate startDate,
        final LocalDate endDate,
        final Integer page,
        final Integer size
    ) {
        return repository.getBankStatement(
            clientId,
            startDate,
            endDate,
            PageRequest.of(page, size)
        ).getContent();
    }
}
