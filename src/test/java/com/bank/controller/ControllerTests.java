package com.bank.controller;

import com.bank.config.TestConfig;
import com.bank.handler.AppResponseEntityExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

abstract class ControllerTests<C> {
    private MockMvc mvc;

    @Autowired
    @InjectMocks
    private C controller;

    @Autowired
    private TestConfig config;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new AppResponseEntityExceptionHandler())
            .build();
    }

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(mvc, notNullValue());
        assertThat(controller, notNullValue());
        assertThat(config, notNullValue());
        assertThat(objectMapper, notNullValue());
    }

    protected MockMvc mvc() {
        return mvc;
    }

    protected C controller() {
        return controller;
    }

    protected TestConfig config() {
        return config;
    }

    protected ObjectMapper objectMapper() {
        return objectMapper;
    }

    protected abstract String getBasePath();

    protected String buildUrl() {
        return buildUrl("", "", Optional.empty());
    }

    protected <ID> String buildUrl(final ID id) {
        return buildUrl(id, "", Optional.empty());
    }

    protected String buildUrl(final String queryParamName, final Optional<String> queryParamValue) {
        return buildUrl("", queryParamName, queryParamValue);
    }

    private String buildUrl(final Object id, final String queryParamName, final Optional<String> queryParamValue) {
        return UriComponentsBuilder
            .fromHttpUrl(config.getBaseUrl())
            .path(getBasePath())
            .pathSegment(id.toString())
            .queryParamIfPresent(queryParamName, queryParamValue)
            .build()
            .toUri()
            .toString();
    }
}
