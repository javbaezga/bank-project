package com.bank.config;

import jakarta.validation.constraints.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Component("configProperties")
@PropertySource("classpath:config.properties")
@ConfigurationProperties
@Validated
public class ConfigProperties {
    @NotNull
    private Account account;
    @NotNull
    private Scheduling scheduling;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Scheduling getScheduling() {
        return scheduling;
    }

    public void setScheduling(Scheduling scheduling) {
        this.scheduling = scheduling;
    }

    @Validated
    public static class Account {
        @NotNull
        @DecimalMin("100.00")
        @DecimalMax("1000000.00")
        @Digits(integer = 7, fraction = 2)
        private BigDecimal dailyQuota;

        public BigDecimal getDailyQuota() {
            return dailyQuota;
        }

        public void setDailyQuota(BigDecimal dailyQuota) {
            this.dailyQuota = dailyQuota;
        }
    }

    @Validated
    public static class Scheduling {
        @NotBlank
        @Pattern(regexp="^(@(annually|yearly|monthly|weekly|daily|hourly|reboot))|(@every (\\d+(ns|us|µs|ms|s|m|h))+)|((((\\d+,)+\\d+|(\\d+(\\/|-)\\d+)|\\d+|\\*) ?){5,7})$")
        private String cron;
        @NotBlank
        @Pattern(regexp="^\\w+\\/\\w+$")
        private String zone;

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }
    }
}
