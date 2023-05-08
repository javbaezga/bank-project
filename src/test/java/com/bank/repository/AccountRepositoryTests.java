package com.bank.repository;

import com.bank.entity.Account;
import com.bank.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class AccountRepositoryTests {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(accountRepository, notNullValue());
        assertThat(clientRepository, notNullValue());
    }

    @Test
    public void findFirstAccountById() {
        assertThat(accountRepository.findById(1L).orElse(null), notNullValue());
    }

    @Test
    public void findFirstAccountByNumber() {
        assertThat(accountRepository.findByNumber("000001").orElse(null), notNullValue());
    }

    @Test
    public void updateFirstAccount() {
        final Account account = accountRepository.findById(1L).orElse(null);
        assertThat(account, notNullValue());
        account.setType(Account.Type.C);
        assertDoesNotThrow(() -> accountRepository.save(account));
    }

    @Test
    public void createNewAccountThenDelete() {
        final Client client = clientRepository.findById(1L).orElse(null);
        assertThat(client, notNullValue());
        final Account account = new Account();
        account.setClient(client);
        account.setNumber("000002");
        account.setType(Account.Type.A);
        account.setInitialBalance(BigDecimal.valueOf(700.00));
        account.setStatus(true);
        assertDoesNotThrow(() -> accountRepository.save(account));
        assertDoesNotThrow(() -> accountRepository.delete(account));
    }
}
