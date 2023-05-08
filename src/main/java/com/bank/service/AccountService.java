package com.bank.service;

import com.bank.dto.AccountDto;
import com.bank.dto.AccountResponseDto;
import com.bank.service.exception.ResourceNotFoundException;

import java.util.List;

public interface AccountService extends CrudService<Long, AccountDto, AccountResponseDto> {
    /**
     * Finds an account by number.
     * @param number Account number.
     * @return Account response DTO.
     * @throws ResourceNotFoundException If entity is not found.
     */
    AccountResponseDto findByNumber(String number);

    /**
     * Finds accounts by client ID.
     * @param clientId Client ID.
     * @return List of accounts.
     */
    List<AccountResponseDto> findByClientId(Long clientId);
}
