package com.bank.service.impl;

import com.bank.dto.AccountDto;
import com.bank.dto.AccountResponseDto;
import com.bank.entity.Account;
import com.bank.mapper.AccountMapper;
import com.bank.repository.AccountRepository;
import com.bank.repository.ClientRepository;
import com.bank.service.AccountService;
import com.bank.service.exception.ResourceNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl extends CrudServiceImpl<
    Long,
    Account,
    AccountDto,
    AccountResponseDto,
    AccountRepository,
    AccountMapper
> implements AccountService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    public AccountServiceImpl(final AccountRepository repository, final AccountMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public AccountResponseDto findByNumber(final @NotBlank String number) {
        return getMapper().entityToResponseDto(
            getRepository().findByNumber(number).orElseThrow(ResourceNotFoundException::new)
        );
    }

    @Override
    public List<AccountResponseDto> findByClientId(final @NotNull Long clientId) {
        final List<Account> accounts = getRepository().findByClientId(clientId).orElseThrow(ResourceNotFoundException::new);
        return accounts.stream().map(getMapper()::entityToResponseDto).toList();
    }

    @Override
    protected void patchEntity(final @NotNull Account entity, final @NotNull AccountDto dto) {
        if (dto.getClientId() != null) {
            entity.setClient(clientRepository.getReferenceById(dto.getClientId()));
        }
        if (dto.getType() != null) {
            entity.setType(Account.Type.values()[dto.getType().ordinal()]);
        }
        if (dto.getInitialBalance() != null) {
            entity.setInitialBalance(dto.getInitialBalance());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
    }
}
