package com.bank.mapper.impl;

import com.bank.dto.ClientDto;
import com.bank.dto.ClientResponseDto;
import com.bank.entity.Client;
import com.bank.entity.Person;
import com.bank.mapper.ClientMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientMapperImpl implements ClientMapper {
    @Override
    public Client dtoToEntity(final ClientDto dto) {
        final Client entity = new Client();
        entity.setFullName(dto.getFullName());
        entity.setGender(Person.Gender.values()[dto.getGender().ordinal()]);
        entity.setAge(dto.getAge());
        entity.setIdNumber(dto.getIdNumber());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    @Override
    public ClientDto entityToDto(final Client entity) {
        final ClientDto dto = new ClientDto();
        dto.setFullName(entity.getFullName());
        dto.setGender(ClientDto.Gender.values()[entity.getGender().ordinal()]);
        dto.setAge(entity.getAge());
        dto.setIdNumber(entity.getIdNumber());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    @Override
    public ClientResponseDto entityToResponseDto(final Client entity) {
        final ClientResponseDto dto = new ClientResponseDto();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setPassword(entity.getPassword());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
