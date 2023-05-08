package com.bank.controller;

import com.bank.dto.MovementDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class MovementControllerTests extends ControllerTests<MovementController> {
    private static final String ACCOUNT_NUMBER = "000001";

    @Override
    protected String getBasePath() {
        return "/movimientos";
    }

    private MovementDto movementTestDto(final double value) {
        final MovementDto dto = new MovementDto();
        dto.setAccountNumber(ACCOUNT_NUMBER);
        dto.setValue(BigDecimal.valueOf(value));
        return dto;
    }

    private ResultMatcher[] movementResponseDtoMatchers() {
        return new ResultMatcher[] {
            jsonPath("$.Id", is(greaterThanOrEqualTo(1))),
            jsonPath("$.['Número Cuenta']", is(ACCOUNT_NUMBER)),
            jsonPath("$.Estado", is(true)),
        };
    }

    @Test
    public void createFirstMovementThenFindIt() throws Exception {
        mvc().perform(post(buildUrl())
            .content(objectMapper().writeValueAsString(movementTestDto(100)))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpectAll(movementResponseDtoMatchers());
        mvc().perform(get(buildUrl(1L))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpectAll(movementResponseDtoMatchers());
    }

    @Test
    public void createDebitMovementThenCreatedStatus() throws Exception {
        mvc().perform(post(buildUrl())
            .content(objectMapper().writeValueAsString(movementTestDto(-100)))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpectAll(movementResponseDtoMatchers());
    }

    @Test
    public void createCreditMovementThenCreatedStatus() throws Exception {
        mvc().perform(post(buildUrl())
            .content(objectMapper().writeValueAsString(movementTestDto(100)))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpectAll(movementResponseDtoMatchers());
    }

    @Test
    public void createZeroMovementThenBadRequestStatus() throws Exception {
        mvc().perform(post(buildUrl())
            .content(objectMapper().writeValueAsString(movementTestDto(0)))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }
}
