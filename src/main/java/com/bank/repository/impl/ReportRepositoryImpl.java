package com.bank.repository.impl;

import com.bank.dto.AccountResponseDto;
import com.bank.dto.BankStatementResponseDto;
import com.bank.entity.Account;
import com.bank.repository.ReportRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ReportRepositoryImpl implements ReportRepository {
    private final EntityManager entityManager;

    @Autowired
    public ReportRepositoryImpl(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<BankStatementResponseDto> getBankStatement(
        final Long clientId,
        final LocalDate startDate,
        final LocalDate endDate,
        final Pageable pageable
    ) {
        try {
            final int pageSize = pageable.getPageSize();
            final List<Tuple> tupleList = entityManager.createNativeQuery(
            """
                    SELECT
                    `mov`.`id`,
                    `mov`.`fecha` `date`,
                    `per`.`nombres` `clientFullName`,
                    `cta`.`numero` `accountNumber`,
                    `cta`.`tipo` `type`,
                    `cta`.`saldo_inicial` `initialBalance`,
                    `cta`.`estado` `status`,
                    `mov`.`valor` `value`,
                    `mov`.`saldo` `balance`
                    FROM `movimiento` `mov`
                    INNER JOIN `cuenta` `cta` ON `cta`.`id` = `mov`.`cuenta_id`
                    INNER JOIN `cliente` `cli` ON `cli`.`id` = `cta`.`cliente_id`
                    INNER JOIN `persona` `per` ON `per`.`id` = `cli`.`id`
                    WHERE
                    `cta`.`cliente_id` = :clientId AND
                    `mov`.`fecha` BETWEEN :startDate AND :endDate
                    ORDER BY `mov`.`fecha` DESC, `mov`.`id` DESC
                    """,
                    Tuple.class
                )
                .setParameter("clientId", clientId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setFirstResult(pageable.getPageNumber() * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
            final List<BankStatementResponseDto> content = tupleList
                .stream()
                .map(tuple -> {
                    final BankStatementResponseDto dto = new BankStatementResponseDto();
                    dto.setId(tuple.get("id", Long.class));
                    dto.setDate(tuple.get("date", Date.class).toLocalDate());
                    dto.setClientFullName(tuple.get("clientFullName", String.class));
                    dto.setAccountNumber(tuple.get("accountNumber", String.class));
                    dto.setType(Account.Type.valueOf(tuple.get("type", String.class)) == Account.Type.A ? AccountResponseDto.Type.Savings : AccountResponseDto.Type.Current);
                    dto.setInitialBalance(tuple.get("initialBalance", BigDecimal.class));
                    dto.setStatus(tuple.get("status", Boolean.class));
                    dto.setValue(tuple.get("value", BigDecimal.class));
                    dto.setBalance(tuple.get("balance", BigDecimal.class));
                    return dto;
                })
                .toList();
            final long total = (long) entityManager.createNativeQuery(
            """ 
                    SELECT
                    COUNT(*)
                    FROM `movimiento` `mov`
                    INNER JOIN `cuenta` `cta` ON `cta`.`id` = `mov`.`cuenta_id`
                    WHERE
                    `cta`.`cliente_id` = :clientId AND
                    `mov`.`fecha` BETWEEN :startDate AND :endDate
                    GROUP BY `cta`.`cliente_id`
                    """,
                    Long.class
                )
                .setParameter("clientId", clientId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
            return new PageImpl<>(content, pageable, total);
        } catch (final NoResultException e) {
            return new PageImpl<>(List.of(), pageable, 0);
        }
    }
}
