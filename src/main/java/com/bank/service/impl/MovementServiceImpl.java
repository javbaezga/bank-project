package com.bank.service.impl;

import com.bank.dto.MovementDto;
import com.bank.dto.MovementResponseDto;
import com.bank.entity.Account;
import com.bank.entity.Movement;
import com.bank.mapper.AccountMapper;
import com.bank.mapper.ClientMapper;
import com.bank.mapper.MovementMapper;
import com.bank.repository.MovementRepository;
import com.bank.service.MovementService;
import com.bank.service.exception.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MovementServiceImpl implements MovementService {
    private final MovementRepository movementRepository;
    private final MovementMapper movementMapper;
    private final AccountMapper accountMapper;
    private final ClientMapper clientMapper;

    @Autowired
    public MovementServiceImpl(
        final MovementRepository movementRepository,
        final MovementMapper movementMapper,
        final AccountMapper accountMapper,
        final ClientMapper clientMapper
    ) {
        this.movementRepository = movementRepository;
        this.movementMapper = movementMapper;
        this.accountMapper = accountMapper;
        this.clientMapper = clientMapper;
    }

    @Override
    public MovementResponseDto getById(final Long id) {
        return movementMapper.entityToResponseDto(
            movementRepository.findById(id).orElseThrow(ResourceNotFoundException::new)
        );
    }

    @Transactional
    @Override
    public MovementResponseDto save(final MovementDto dto) {
        final Movement entity = movementMapper.dtoToEntity(dto);
        final Account account = entity.getAccount();
        if (!account.getClient().getStatus()) {
            throw new ClientDisabledException(clientMapper.entityToResponseDto(account.getClient()));
        }
        if (!account.getStatus()) {
            throw new AccountDisabledException(accountMapper.entityToResponseDto(account));
        }
        final BigDecimal value = entity.getValue();
        final BigDecimal newDailyBalance = account.getDailyBalance().add(value);
        if (newDailyBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new DailyQuotaExceededException(account.getDailyBalance());
        }
        if (entity.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new NotAvailableBalanceException(entity.getBalance().subtract(value));
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            entity.getAccount().setDailyBalance(newDailyBalance);
        }
        entity.getAccount().setBalance(entity.getBalance());
        return movementMapper.entityToResponseDto(movementRepository.save(entity));
    }
}
