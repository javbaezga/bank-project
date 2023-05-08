package com.bank.mapper.impl;

import com.bank.config.ConfigProperties;
import com.bank.dto.AccountDto;
import com.bank.dto.AccountResponseDto;
import com.bank.entity.Account;
import com.bank.entity.Client;
import com.bank.mapper.AccountMapper;
import com.bank.repository.ClientRepository;
import com.bank.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AccountMapperImpl implements AccountMapper {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ConfigProperties configProperties;

    /**
     * Gets a client by ID.
     * @param id Client ID.
     * @return Client.
     * @throws ResourceNotFoundException If entity is not found.
     */
    private Client getClientById(final Long id) {
        return clientRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Account dtoToEntity(final AccountDto dto) {
        final Account entity = new Account();
        entity.setClient(getClientById(dto.getClientId()));
        entity.setNumber(dto.getNumber());
        entity.setType(Account.Type.values()[dto.getType().ordinal()]);
        entity.setInitialBalance(dto.getInitialBalance());
        if (entity.getBalance() == null) {
            entity.setBalance(dto.getInitialBalance());
        }
        if (entity.getDailyBalance() == null) {
            entity.setDailyBalance(configProperties.getAccount().getDailyQuota());
            entity.setDailyBalanceResetDate(LocalDate.now());
        }
        entity.setStatus(dto.getStatus());
        return entity;
    }

    @Override
    public AccountDto entityToDto(final Account entity) {
        final AccountDto dto = new AccountDto();
        dto.setClientId(entity.getClient().getId());
        dto.setNumber(entity.getNumber());
        dto.setType(AccountDto.Type.values()[entity.getType().ordinal()]);
        dto.setInitialBalance(entity.getInitialBalance());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    @Override
    public AccountResponseDto entityToResponseDto(final Account entity) {
        final AccountResponseDto dto = new AccountResponseDto();
        dto.setId(entity.getId());
        dto.setNumber(entity.getNumber());
        dto.setType(AccountResponseDto.Type.values()[entity.getType().ordinal()]);
        dto.setInitialBalance(entity.getInitialBalance());
        dto.setStatus(entity.getStatus());
        dto.setClientFullName(entity.getClient().getFullName());
        return dto;
    }
}
