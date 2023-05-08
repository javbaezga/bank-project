package com.bank.mapper.impl;

import com.bank.dto.AccountResponseDto;
import com.bank.dto.MovementDto;
import com.bank.dto.MovementResponseDto;
import com.bank.entity.Account;
import com.bank.entity.Movement;
import com.bank.mapper.MovementMapper;
import com.bank.repository.AccountRepository;
import com.bank.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class MovementMapperImpl implements MovementMapper {
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Gets an account by number.
     * @param accountNumber Account number.
     * @return Account.
     * @throws ResourceNotFoundException If entity is not found.
     */
    private Account getAccountByNumber(final String accountNumber) {
        return accountRepository.findByNumber(accountNumber).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Movement dtoToEntity(final MovementDto dto) {
        final Account account = getAccountByNumber(dto.getAccountNumber());
        final BigDecimal value = dto.getValue();
        final Movement entity = new Movement();
        entity.setDate(LocalDate.now());
        entity.setType(value.compareTo(BigDecimal.ZERO) < 0 ? Movement.Type.D : Movement.Type.C);
        entity.setValue(value);
        entity.setBalance(account.getBalance().add(value));
        entity.setAccount(account);
        return entity;
    }

    @Override
    public MovementDto entityToDto(final Movement entity) {
        final MovementDto dto = new MovementDto();
        dto.setAccountNumber(entity.getAccount().getNumber());
        dto.setValue(entity.getValue());
        return dto;
    }

    @Override
    public MovementResponseDto entityToResponseDto(final Movement entity) {
        final Account account = entity.getAccount();
        final MovementResponseDto dto = new MovementResponseDto();
        dto.setId(entity.getId());
        dto.setAccountNumber(account.getNumber());
        dto.setType(AccountResponseDto.Type.values()[account.getType().ordinal()]);
        dto.setInitialBalance(account.getInitialBalance());
        dto.setStatus(account.getStatus());
        dto.setDescription(String.format(
            "%s de %s",
            entity.getType() == Movement.Type.C ? "Depósito" : "Retiro",
            entity.getValue().abs()
        ));
        return dto;
    }
}
