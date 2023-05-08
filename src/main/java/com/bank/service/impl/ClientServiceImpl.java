package com.bank.service.impl;

import com.bank.dto.ClientDto;
import com.bank.dto.ClientResponseDto;
import com.bank.entity.Client;
import com.bank.entity.Person;
import com.bank.mapper.ClientMapper;
import com.bank.repository.ClientRepository;
import com.bank.service.ClientService;
import com.bank.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl extends CrudServiceImpl<
    Long,
    Client,
    ClientDto,
    ClientResponseDto,
    ClientRepository,
    ClientMapper
> implements ClientService {
    @Autowired
    public ClientServiceImpl(final ClientRepository repository, final ClientMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ClientResponseDto findByIdNumber(final String idNumber) {
        return getMapper().entityToResponseDto(
            getRepository().findByIdNumber(idNumber).orElseThrow(ResourceNotFoundException::new)
        );
    }

    @Override
    protected void patchEntity(final Client entity, final ClientDto dto) {
        if (dto.getFullName() != null) {
            entity.setFullName(dto.getFullName());
        }
        if (dto.getGender() != null) {
            entity.setGender(Person.Gender.values()[dto.getGender().ordinal()]);
        }
        if (dto.getAge() != null) {
            entity.setAge(dto.getAge());
        }
        if (dto.getIdNumber() != null) {
            entity.setIdNumber(dto.getIdNumber());
        }
        if (dto.getAddress() != null) {
            entity.setAddress(dto.getAddress());
        }
        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }
        if (dto.getUsername() != null) {
            entity.setUsername(dto.getUsername());
        }
        if (dto.getPassword() != null) {
            entity.setPassword(dto.getPassword());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
    }
}
