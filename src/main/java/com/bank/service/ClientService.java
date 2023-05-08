package com.bank.service;

import com.bank.dto.ClientDto;
import com.bank.dto.ClientResponseDto;
import com.bank.service.exception.ResourceNotFoundException;

public interface ClientService extends CrudService<Long, ClientDto, ClientResponseDto> {
    /**
     * Finds a client by ID number.
     * @param idNumber ID number.
     * @return Client response DTO.
     * @throws ResourceNotFoundException If entity is not found.
     */
    ClientResponseDto findByIdNumber(String idNumber);
}
