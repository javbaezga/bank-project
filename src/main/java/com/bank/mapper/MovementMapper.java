package com.bank.mapper;

import com.bank.dto.MovementDto;
import com.bank.dto.MovementResponseDto;
import com.bank.entity.Movement;

public interface MovementMapper extends Mapper<Movement, MovementDto, MovementResponseDto> {
}
