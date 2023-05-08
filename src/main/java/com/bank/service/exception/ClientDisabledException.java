package com.bank.service.exception;

import com.bank.dto.ClientResponseDto;

public class ClientDisabledException extends RuntimeException {
    private final ClientResponseDto client;

    public ClientDisabledException(final ClientResponseDto client) {
        super("Client disabled");
        this.client = client;
    }

    public ClientResponseDto getClient() {
        return client;
    }
}
