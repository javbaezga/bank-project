package com.bank.controller;

import com.bank.dto.ClientDto;
import com.bank.dto.ClientResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Optional;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ClientControllerTests extends ControllerTests<ClientController> {
    @Override
    protected String getBasePath() {
        return "/clientes";
    }

    private ClientDto clientTestDto() {
        final ClientDto dto = new ClientDto();
        dto.setFullName("Marianela Montalvo");
        dto.setGender(ClientDto.Gender.Female);
        dto.setAge(18);
        dto.setIdNumber("1713458920");
        dto.setAddress("Amazonas y Naciones Unidas");
        dto.setPhone("097548965");
        dto.setUsername("mmontalvo");
        dto.setPassword("56785678");
        dto.setStatus(true);
        return dto;
    }

    private ClientResponseDto clientResponseTestDto() {
        final ClientResponseDto responseDto = new ClientResponseDto();
        responseDto.setId(2L);
        responseDto.setFullName("Marianela Montalvo");
        responseDto.setAddress("Amazonas y Naciones Unidas");
        responseDto.setPhone("097548965");
        responseDto.setPassword("56785678");
        responseDto.setStatus(true);
        return responseDto;
    }

    private ResultMatcher[] clientResponseDtoMatchers(final ClientResponseDto responseDto) {
        return new ResultMatcher[] {
            jsonPath("$.Id", is(greaterThanOrEqualTo(1))),
            jsonPath("$.Nombres", is(responseDto.getFullName())),
            jsonPath("$.Dirección", is(responseDto.getAddress())),
            jsonPath("$.Contraseña", is(responseDto.getPassword())),
            jsonPath("$.Estado", is(responseDto.getStatus()))
        };
    }

    @Test
    public void findFirstClientById() throws Exception {
        mvc().perform(get(buildUrl(1L))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void findFirstClientByIdNumber() throws Exception {
        mvc().perform(get(buildUrl("identificacion", Optional.of("1234567890")))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void createClientThenUpdateAndDelete() throws Exception {
        final MockMvc mvc = mvc();
        final ClientDto dto = clientTestDto();
        // Create
        final ClientResponseDto responseDto = objectMapper().readValue(
            mvc.perform(post(buildUrl())
                .content(objectMapper().writeValueAsString(dto))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpectAll(clientResponseDtoMatchers(clientResponseTestDto()))
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ClientResponseDto.class
        );
        // Update DTOs
        dto.setFullName(dto.getFullName() + "1");
        dto.setAge(dto.getAge() + 1);
        dto.setAddress(dto.getAddress() + "1");
        dto.setUsername(dto.getUsername() + "1");
        dto.setPassword(dto.getPassword() + "1");
        responseDto.setFullName(dto.getFullName());
        responseDto.setAddress(dto.getAddress());
        responseDto.setPassword(dto.getPassword());
        // Update
        mvc.perform(put(buildUrl(responseDto.getId()))
            .content(objectMapper().writeValueAsString(dto))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpectAll(clientResponseDtoMatchers(responseDto));

        // Delete
        mvc.perform(delete(buildUrl(responseDto.getId()))
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpectAll(clientResponseDtoMatchers(responseDto));
    }
}
