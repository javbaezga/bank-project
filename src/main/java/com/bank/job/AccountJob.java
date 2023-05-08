package com.bank.job;

import com.bank.config.ConfigProperties;
import com.bank.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AccountJob {
    private static final Logger logger = LoggerFactory.getLogger(AccountJob.class);
    private final AccountRepository accountRepository;
    private final ConfigProperties configProperties;

    @Autowired
    public AccountJob(
        final AccountRepository accountRepository,
        final ConfigProperties configProperties
    ) {
        this.accountRepository = accountRepository;
        this.configProperties = configProperties;
    }

    @Scheduled(
        cron="#{configProperties.scheduling.cron}",
        zone="#{configProperties.scheduling.zone}"
    )
    @Async
    public void refreshDailyBalance() {
        logger.debug("refreshDailyBalance() - START");
        accountRepository.updateDailyBalances(configProperties.getAccount().getDailyQuota(), LocalDate.now());
        logger.debug("refreshDailyBalance() - END");
    }
}
