package com.bank.service.impl;

import com.bank.mapper.Mapper;
import com.bank.service.CrudService;
import com.bank.service.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CRUD service implementation class.
 * @param <ID> ID type.
 * @param <E> Entity type.
 * @param <DTO> DTO type.
 * @param <RDTO> Response DTO type.
 * @param <REPO> JPA repository type.
 * @param <MAPPER> Mapper type.
 */
abstract class CrudServiceImpl<
    ID,
    E,
    DTO,
    RDTO,
    REPO extends JpaRepository<E, ID>,
    MAPPER extends Mapper<E, DTO, RDTO>
> implements CrudService<ID, DTO, RDTO> {
    private final REPO repository;
    private final MAPPER mapper;

    public CrudServiceImpl(final REPO repository, final MAPPER mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Gets JPA repository.
     * @return JPA repository.
     */
    protected REPO getRepository() {
        return this.repository;
    }

    /**
     * Gets mapper.
     * @return Mapper.
     */
    protected MAPPER getMapper() {
        return this.mapper;
    }

    /**
     * Gets entity by ID.
     * @param id ID.
     * @return Entity.
     * @throws ResourceNotFoundException If entity is not found.
     */
    protected E getEntityById(final ID id) {
        return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public RDTO getById(final ID id) {
        return mapper.entityToResponseDto(getEntityById(id));
    }

    @Transactional
    @Override
    public RDTO save(final DTO dto) {
        return mapper.entityToResponseDto(repository.save(mapper.dtoToEntity(dto)));
    }

    @Transactional
    @Override
    public RDTO update(final ID id, final DTO dto) {
        final E entity = getEntityById(id);
        patchEntity(entity, dto);
        return mapper.entityToResponseDto(repository.save(entity));
    }

    /**
     * Patches entity using DTO data.
     * @param entity Entity.
     * @param dto DTO.
     */
    protected abstract void patchEntity(final E entity, final DTO dto);

    @Transactional
    @Override
    public RDTO delete(final ID id) {
        final E entity = getEntityById(id);
        repository.deleteById(id);
        return mapper.entityToResponseDto(entity);
    }
}
