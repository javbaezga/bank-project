package com.bank.repository;

import com.bank.dto.BankStatementResponseDto;
import com.bank.entity.Account;
import com.bank.entity.Movement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class ReportRepositoryTests {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private MovementRepository movementRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(reportRepository, notNullValue());
        assertThat(movementRepository, notNullValue());
        assertThat(accountRepository, notNullValue());
    }

    private Movement createMovement(
        final Account account,
        final LocalDate date,
        final BigDecimal balance,
        final BigDecimal value
    ) {
        final Movement movement = new Movement();
        movement.setBalance(balance);
        movement.setDate(date);
        movement.setType(value.compareTo(BigDecimal.ZERO) < 0 ? Movement.Type.D : Movement.Type.C);
        movement.setValue(value);
        movement.setAccount(account);
        return movement;
    }

    @Test
    public void createMovementsThenGetBankStatementReport() {
        final Account account = accountRepository.findById(1L).orElse(null);
        assertThat(account, notNullValue());
        final LocalDate date = LocalDate.now();
        final List<Pair<BigDecimal, BigDecimal>> pairs = List.of(
            Pair.of(BigDecimal.valueOf(500), BigDecimal.valueOf(500)),
            Pair.of(BigDecimal.valueOf(400), BigDecimal.valueOf(-100)),
            Pair.of(BigDecimal.valueOf(300), BigDecimal.valueOf(-100)),
            Pair.of(BigDecimal.valueOf(200), BigDecimal.valueOf(-100)),
            Pair.of(BigDecimal.valueOf(500), BigDecimal.valueOf(300))
        );
        pairs.forEach(pair ->
            assertDoesNotThrow(() ->
                movementRepository.save(createMovement(account, date, pair.getFirst(), pair.getSecond())))
        );
        final Page<BankStatementResponseDto> response = reportRepository.getBankStatement(
            account.getClient().getId(),
            date,
            date,
            PageRequest.of(0, 10)
        );
        assertThat(response.getContent().size(), is(greaterThanOrEqualTo(pairs.size())));
    }
}
