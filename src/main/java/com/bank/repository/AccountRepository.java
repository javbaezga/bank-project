package com.bank.repository;

import com.bank.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Finds an account by number.
     * @param number Account number.
     * @return Account.
     */
    Optional<Account> findByNumber(String number);

    /**
     * Finds accounts by client ID.
     * @param clientId Client ID.
     * @return List of accounts.
     */
    Optional<List<Account>> findByClientId(Long clientId);

    /**
     * Updates accounts daily balance.
     * @param dailyBalance Daily balance.
     * @param resetDate Reset date.
     * @return Number of accounts updated.
     */
    @Transactional
    @Modifying(clearAutomatically=true)
    @Query("UPDATE Account SET dailyBalance = :dailyBalance, dailyBalanceResetDate = :resetDate WHERE dailyBalanceResetDate IS NULL OR dailyBalanceResetDate < :resetDate")
    int updateDailyBalances(
        @Param("dailyBalance") BigDecimal dailyBalance,
        @Param("resetDate") LocalDate resetDate
    );
}
