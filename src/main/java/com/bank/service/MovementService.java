package com.bank.service;

import com.bank.dto.MovementDto;
import com.bank.dto.MovementResponseDto;
import com.bank.service.exception.*;

public interface MovementService {
    /**
     * Gets a movement by ID.
     * @param id ID.
     * @return Movement DTO.
     * @throws ResourceNotFoundException If entity is not found.
     * @throws ClientDisabledException If client is disabled.
     * @throws AccountDisabledException If account is disabled.
     * @throws DailyQuotaExceededException If daily quota is exceeded.
     * @throws NotAvailableBalanceException If balance is not available.
     */
    MovementResponseDto getById(Long id);

    /**
     * Saves a movement.
     * @param dto Movement DTO.
     * @return Movement response DTO.
     */
    MovementResponseDto save(MovementDto dto);
}
