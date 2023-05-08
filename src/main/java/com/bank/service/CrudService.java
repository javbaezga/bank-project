package com.bank.service;

import com.bank.service.exception.ResourceNotFoundException;

/**
 * CRUD service interface.
 * @param <ID> Key type.
 * @param <DTO> DTO type.
 * @param <RDTO> Response DTO type.
 */
public interface CrudService<ID, DTO, RDTO> {
    /**
     * Gets data by ID.
     * @param id ID.
     * @return Response DTO.
     * @throws ResourceNotFoundException If entity is not found.
     */
    RDTO getById(ID id);

    /**
     * Saves data.
     * @param dto DTO.
     * @return Response DTO.
     */
    RDTO save(DTO dto);

    /**
     * Updates data.
     * @param id ID.
     * @param dto DTO.
     * @return Response DTO.
     * @throws ResourceNotFoundException If entity is not found.
     */
    RDTO update(ID id, DTO dto);

    /**
     * Deletes data.
     * @param id ID.
     * @return Response DTO.
     * @throws ResourceNotFoundException If entity is not found.
     */
    RDTO delete(ID id);
}
