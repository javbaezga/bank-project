package com.bank.repository;

import com.bank.entity.Account;
import com.bank.entity.Movement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class MovementRepositoryTests {
    @Autowired
    private MovementRepository movementRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(movementRepository, notNullValue());
        assertThat(accountRepository, notNullValue());
    }

    private Movement createMovement(final Account account, final BigDecimal balance, final BigDecimal value) {
        final Movement movement = new Movement();
        movement.setBalance(balance);
        movement.setDate(LocalDate.now());
        movement.setType(value.compareTo(BigDecimal.ZERO) < 0 ? Movement.Type.D : Movement.Type.C);
        movement.setValue(value);
        movement.setAccount(account);
        return movement;
    }

    @Test
    public void createDebitMovementThenDelete() {
        final Account account = accountRepository.findById(1L).orElse(null);
        assertThat(account, notNullValue());
        final Movement movement = createMovement(account, BigDecimal.ZERO, BigDecimal.valueOf(-100));
        assertDoesNotThrow(() -> movementRepository.save(movement));
        assertDoesNotThrow(() -> movementRepository.delete(movement));
    }

    @Test
    public void createCreditMovementThenDelete() {
        final Account account = accountRepository.findById(1L).orElse(null);
        assertThat(account, notNullValue());
        final Movement movement = createMovement(account, BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        assertDoesNotThrow(() -> movementRepository.save(movement));
        assertDoesNotThrow(() -> movementRepository.delete(movement));
    }

    @Test
    public void createMovementThenFind() {
        final Account account = accountRepository.findById(1L).orElse(null);
        assertThat(account, notNullValue());
        final Movement movement = createMovement(account, BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        assertDoesNotThrow(() -> movementRepository.save(movement));
        assertDoesNotThrow(() -> movementRepository.findById(movement.getId()).orElseThrow());
    }
}
