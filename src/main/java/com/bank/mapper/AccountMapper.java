package com.bank.mapper;

import com.bank.dto.AccountDto;
import com.bank.dto.AccountResponseDto;
import com.bank.entity.Account;

public interface AccountMapper extends Mapper<Account, AccountDto, AccountResponseDto> {
}
