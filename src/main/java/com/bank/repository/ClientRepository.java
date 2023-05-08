package com.bank.repository;

import com.bank.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    /**
     * Finds a client by ID number.
     * @param idNumber Client ID number.
     * @return Client.
     */
    Optional<Client> findByIdNumber(String idNumber);
}
