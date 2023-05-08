package com.bank.mapper;

/**
 * Mapper interface.
 * @param <E> Entity type.
 * @param <DTO> DTO type.
 * @param <RDTO> Response DTO type.
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper<E, DTO, RDTO> {
    /**
     * Converts from DTO to JPA entity.
     * @param dto DTO instance.
     * @return JPA entity.
     */
    E dtoToEntity(DTO dto);

    /**
     * Converts from JPA entity to DTO.
     * @param entity JPA entity.
     * @return DTO.
     */
    DTO entityToDto(E entity);

    /**
     * Converts from JPA entity to response DTO.
     * @param entity JPA entity.
     * @return Response DTO.
     */
    RDTO entityToResponseDto(E entity);
}